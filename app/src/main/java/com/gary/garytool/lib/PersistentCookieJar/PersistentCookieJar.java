/*
 * Copyright (C) 2016 Francisco José Montiel Navarro.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gary.garytool.lib.PersistentCookieJar;

import com.gary.garytool.lib.PersistentCookieJar.cache.CookieCache;
import com.gary.garytool.lib.PersistentCookieJar.cache.SetCookieCache;
import com.gary.garytool.lib.PersistentCookieJar.persistence.CookiePersistor;
import com.gary.garytool.lib.PersistentCookieJar.persistence.SharedPrefsCookiePersistor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import okhttp3.Cookie;
import okhttp3.HttpUrl;

public class PersistentCookieJar implements ClearableCookieJar {

    private CookieCache cache;
    private CookiePersistor persistor;

    public PersistentCookieJar(SetCookieCache cache, SharedPrefsCookiePersistor persistor) {
        this.cache = cache;
        this.persistor = persistor;

        this.cache.addAll(persistor.loadAll());
    }

    @Override
    synchronized public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cache.addAll(cookies);
        persistor.saveAll(cookies);
    }

    @Override
    synchronized public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> removedCookies = new ArrayList<>();
        List<Cookie> validCookies = new ArrayList<>();

        for (Iterator<Cookie> it = cache.iterator(); it.hasNext(); ) {
            Cookie currentCookie = it.next();

            //webchatid=; usertype=4; ASP.NET_SessionId=fuesnjrrs0bvzvzkbqxehej1; username=王浩远; otherid=13; userid=221; .ASPXAUTH=               ;


            if (isCookieExpired(currentCookie)) {
                removedCookies.add(currentCookie);
                it.remove();

            } else if (currentCookie.matches(url)) {
                try {
                    currentCookie=new Cookie.Builder().domain(currentCookie.domain()).name(currentCookie.name()).value(URLEncoder.encode(currentCookie.value(),"utf-8")).build();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                validCookies.add(currentCookie);
            }
        }

        persistor.removeAll(removedCookies);

        return  validCookies;
    }

    private static boolean isCookieExpired(Cookie cookie) {
        return cookie.expiresAt() < System.currentTimeMillis();
    }

    synchronized public void clear() {
        cache.clear();
        persistor.clear();
    }
}
