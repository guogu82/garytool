package com.gary.garytool.model;

/**
 * Created by Gary on 2016/5/26.
 */
public class DataApi {
  String DataForOrderCancel="respObj";
  //JSONObject respObj = new JSONObject(responseInfo.result);
  //{"total":266,"rows":[{"caneceltime":"2016-05-25 16:00","areaname":"凤凰城凤锦苑1街凤锦一街负一层(8-15栋)002车位","remark":"","state":3,"owname":"大笨强","tech_phone":"10831708841","est_tech_finishtime":null,"state_desc":"已取消","createtime":"2016-05-25 15:24","owner_phone":"13427525758","est_starttime":null,"techname":"朱志誉","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"","cancel_reason":"","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":null,"proname":"内饰深层养护干洗","actStartAndElapsedTime":"","finishtime":"2016-05-25T16:00:00","owner_take_key_time":null,"carnum":"粤TT1233","orderid":7344},{"caneceltime":"","areaname":"凤凰城凤锦苑1街凤锦一街负一层(8-15栋)002车位","remark":"4585586696","state":5,"owname":"大笨强","tech_phone":"10831708841","est_tech_finishtime":null,"state_desc":"已取消","createtime":"2016-05-25 11:20","owner_phone":"13427525758","est_starttime":null,"techname":"朱志誉","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"","cancel_reason":"不想洗了","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":null,"proname":"釉蜡内外精洗","actStartAndElapsedTime":"","finishtime":"2016-05-25T11:25:35","owner_take_key_time":null,"carnum":"粤TT1233","orderid":7343},{"caneceltime":"2016-05-25 11:12","areaname":"凤凰城凤锦苑1街凤锦一街负一层(8-15栋)002车位","remark":"技师:陈进浩 帮车主下单","state":7,"owname":"大笨强","tech_phone":"10831708841","est_tech_finishtime":"2016-05-25T13:32:00","state_desc":"已取消","createtime":"2016-05-25 11:12","owner_phone":"13427525758","est_starttime":"2016-05-25T12:22:00","techname":"朱志誉","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"12:22施工, 耗时40分钟","cancel_reason":"不想洗了","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":"2016-05-25T19:00:00","proname":"釉蜡外洗","actStartAndElapsedTime":"","finishtime":"2016-05-25T11:12:47","owner_take_key_time":null,"carnum":"粤TT1233","orderid":7342},{"caneceltime":"2016-05-25 11:11","areaname":"凤凰城凤锦苑1街凤锦一街负一层(8-15栋)002车位","remark":"技师:陈进浩 帮车主下单","state":7,"owname":"大笨强","tech_phone":"10831708841","est_tech_finishtime":"2016-05-25T13:31:00","state_desc":"已取消","createtime":"2016-05-25 11:11","owner_phone":"13427525758","est_starttime":"2016-05-25T12:21:00","techname":"朱志誉","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"12:21施工, 耗时40分钟","cancel_reason":"不想洗了","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":"2016-05-25T19:00:00","proname":"釉蜡外洗","actStartAndElapsedTime":"","finishtime":"2016-05-25T11:11:33","owner_take_key_time":null,"carnum":"粤TT1233","orderid":7341},{"caneceltime":"2016-05-25 11:10","areaname":"凤凰城凤锦苑1街凤锦一街负一层(8-15栋)002车位","remark":"技师:陈进浩 帮车主下单","state":7,"owname":"大笨强","tech_phone":"10083170880","est_tech_finishtime":"2016-05-25T12:21:00","state_desc":"已取消","createtime":"2016-05-25 11:10","owner_phone":"13427525758","est_starttime":"2016-05-25T11:11:00","techname":"何晓炜","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"11:11施工, 耗时40分钟","cancel_reason":"不想洗了","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":"2016-05-25T19:00:00","proname":"釉蜡外洗","actStartAndElapsedTime":"","finishtime":"2016-05-25T11:10:59","owner_take_key_time":null,"carnum":"粤TT1233","orderid":7340},{"caneceltime":"2016-05-25 11:10","areaname":"凤凰城凤锦苑1街凤锦一街负一层(8-15栋)002车位","remark":"技师:陈进浩 帮车主下单","state":7,"owname":"大笨强","tech_phone":"10831708841","est_tech_finishtime":"2016-05-25T13:30:00","state_desc":"已取消","createtime":"2016-05-25 11:10","owner_phone":"13427525758","est_starttime":"2016-05-25T12:20:00","techname":"朱志誉","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"12:20施工, 耗时40分钟","cancel_reason":"不想洗了","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":"2016-05-25T19:00:00","proname":"釉蜡外洗","actStartAndElapsedTime":"","finishtime":"2016-05-25T11:10:21","owner_take_key_time":null,"carnum":"粤TT1233","orderid":7339},{"caneceltime":"2016-05-25 11:10","areaname":"凤凰城凤锦苑1街凤锦一街负一层(8-15栋)002车位","remark":"技师:陈进浩 帮车主下单","state":7,"owname":"大笨强","tech_phone":"10083170880","est_tech_finishtime":"2016-05-25T12:20:00","state_desc":"已取消","createtime":"2016-05-25 11:09","owner_phone":"13427525758","est_starttime":"2016-05-25T11:10:00","techname":"何晓炜","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"11:10施工, 耗时40分钟","cancel_reason":"不想洗了","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":"2016-05-25T19:00:00","proname":"釉蜡外洗","actStartAndElapsedTime":"","finishtime":"2016-05-25T11:10:00","owner_take_key_time":null,"carnum":"粤TT1233","orderid":7338},{"caneceltime":"2016-05-25 11:09","areaname":"凤凰城凤锦苑1街凤锦一街负一层(8-15栋)002车位","remark":"技师:陈进浩 帮车主下单","state":7,"owname":"大笨强","tech_phone":"10083170880","est_tech_finishtime":"2016-05-25T12:19:00","state_desc":"已取消","createtime":"2016-05-25 11:08","owner_phone":"13427525758","est_starttime":"2016-05-25T11:09:00","techname":"何晓炜","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"11:09施工, 耗时40分钟","cancel_reason":"不想洗了","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":"2016-05-25T19:00:00","proname":"釉蜡外洗","actStartAndElapsedTime":"","finishtime":"2016-05-25T11:09:19","owner_take_key_time":null,"carnum":"粤TT1233","orderid":7337},{"caneceltime":"2016-05-25 11:07","areaname":"凤凰城凤锦苑1街凤锦一街负一层(8-15栋)002车位","remark":"技师:陈进浩 帮车主下单","state":7,"owname":"大笨强","tech_phone":"10083170880","est_tech_finishtime":"2016-05-25T12:17:00","state_desc":"已取消","createtime":"2016-05-25 11:07","owner_phone":"13427525758","est_starttime":"2016-05-25T11:07:00","techname":"何晓炜","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"11:07施工, 耗时40分钟","cancel_reason":"不想洗了","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":"2016-05-25T19:00:00","proname":"釉蜡外洗","actStartAndElapsedTime":"","finishtime":"2016-05-25T11:07:30","owner_take_key_time":null,"carnum":"粤TT1233","orderid":7336},{"caneceltime":"2016-05-25 11:07","areaname":"凤凰城凤锦苑1街凤锦一街负一层(8-15栋)002车位","remark":"","state":7,"owname":"大笨强","tech_phone":"10831708841","est_tech_finishtime":"2016-05-25T13:27:00","state_desc":"已取消","createtime":"2016-05-25 11:06","owner_phone":"13427525758","est_starttime":"2016-05-25T12:17:00","techname":"朱志誉","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"12:17施工, 耗时40分钟","cancel_reason":"不想洗了","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":"2016-05-25T19:00:00","proname":"釉蜡外洗","actStartAndElapsedTime":"","finishtime":"2016-05-25T11:07:10","owner_take_key_time":null,"carnum":"粤TT1233","orderid":7335},{"caneceltime":"2016-05-25 09:55","areaname":"凤凰城凤锦苑1街凤锦一街负一层(8-15栋)396车位","remark":"","state":3,"owname":"大笨强","tech_phone":"10883170880","est_tech_finishtime":null,"state_desc":"已取消","createtime":"2016-05-25 09:27","owner_phone":"18883170884","est_starttime":null,"techname":"何衣庆","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"","cancel_reason":"","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":null,"proname":"釉蜡内外精洗","actStartAndElapsedTime":"","finishtime":"2016-05-25T10:00:00","owner_take_key_time":null,"carnum":"粤ARU727","orderid":7334},{"caneceltime":"2016-05-25 09:55","areaname":"凤凰城凤锦苑1街凤锦一街负一层(8-15栋)396车位","remark":"","state":3,"owname":"大笨强","tech_phone":"10831708841","est_tech_finishtime":null,"state_desc":"已取消","createtime":"2016-05-25 09:24","owner_phone":"13427525758","est_starttime":null,"techname":"朱志誉","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"","cancel_reason":"","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":null,"proname":"釉蜡内外精洗","actStartAndElapsedTime":"","finishtime":"2016-05-25T10:00:00","owner_take_key_time":null,"carnum":"粤ARU727","orderid":7333},{"caneceltime":"","areaname":"凤凰城凤锦苑1街凤锦一街负一层(8-15栋)396车位","remark":"","state":3,"owname":"大笨强","tech_phone":"10831708841","est_tech_finishtime":null,"state_desc":"已取消","createtime":"2016-05-25 09:23","owner_phone":"13427525758","est_starttime":null,"techname":"朱志誉","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"","cancel_reason":"","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":null,"proname":"釉蜡内外精洗","actStartAndElapsedTime":"","finishtime":"2016-05-25T00:00:00","owner_take_key_time":null,"carnum":"粤ARU727","orderid":7332},{"caneceltime":"2016-05-25 09:22","areaname":"凤凰城凤锦苑1街凤锦一街负一层(8-15栋)005车位","remark":"","state":7,"owname":"大笨强","tech_phone":"10083170880","est_tech_finishtime":"2016-05-25T10:32:00","state_desc":"已取消","createtime":"2016-05-25 09:22","owner_phone":"13427525758","est_starttime":"2016-05-25T09:22:00","techname":"何晓炜","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"09:22施工, 耗时40分钟","cancel_reason":"晚点再下","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":"2016-05-25T12:00:00","proname":"釉蜡外洗","actStartAndElapsedTime":"","finishtime":"2016-05-25T09:22:37","owner_take_key_time":null,"carnum":"粤TTTTEE","orderid":7331},{"caneceltime":"2016-05-25 22:31","areaname":"凤凰城凤馨苑1街1栋三区负一层22车位","remark":"","state":7,"owname":"大笨强","tech_phone":"10427525758","est_tech_finishtime":null,"state_desc":"已取消","createtime":"2016-05-25 09:22","owner_phone":"13427525758","est_starttime":null,"techname":"陈进浩","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"","cancel_reason":"","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":null,"proname":"釉蜡内外精洗","actStartAndElapsedTime":"","finishtime":"2016-05-25T22:31:00","owner_take_key_time":null,"carnum":"粤TTTTE2","orderid":7330},{"caneceltime":"2016-05-23 19:39","areaname":"凤凰城凤锦苑1街凤锦一街负一层(8-15栋)002车位","remark":"","state":7,"owname":"江山笑烟雨遥","tech_phone":"10883170880","est_tech_finishtime":"2016-05-23T20:37:00","state_desc":"已取消","createtime":"2016-05-23 19:27","owner_phone":"18883170884","est_starttime":"2016-05-23T19:27:00","techname":"唐章龙","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"19:27施工, 耗时40分钟","cancel_reason":"其他","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":"2016-05-24T06:00:00","proname":"釉蜡外洗","actStartAndElapsedTime":"","finishtime":"2016-05-23T19:39:00","owner_take_key_time":null,"carnum":"粤C12345","orderid":7329},{"caneceltime":"2016-05-24 15:13","areaname":"凤凰城凤锦苑1街凤锦一街负一层(8-15栋)088车位","remark":"","state":7,"owname":"秋千","tech_phone":"10883170880","est_tech_finishtime":"2016-05-23T17:54:00","state_desc":"已取消","createtime":"2016-05-23 13:44","owner_phone":"13822161124","est_starttime":"2016-05-23T16:44:00","techname":"何衣庆","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"16:44施工, 耗时40分钟","cancel_reason":"不想洗了","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":"2016-05-23T19:00:00","proname":"釉蜡外洗","actStartAndElapsedTime":"","finishtime":"2016-05-24T15:13:59","owner_take_key_time":null,"carnum":"无牌38550","orderid":7328},{"caneceltime":"","areaname":"凤凰城凤锦苑1街凤锦一街负一层(8-15栋)005车位","remark":"","state":5,"owname":"大笨强","tech_phone":"10083170880","est_tech_finishtime":null,"state_desc":"已取消","createtime":"2016-05-23 11:16","owner_phone":"13427525758","est_starttime":null,"techname":"何晓炜","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"","cancel_reason":"晚点再下","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":null,"proname":"深度护理洗车","actStartAndElapsedTime":"","finishtime":"2016-05-23T11:16:49","owner_take_key_time":null,"carnum":"粤TTTTE2","orderid":7327},{"caneceltime":"2016-05-23 10:24","areaname":"凤凰城凤锦苑1街凤锦一街负一层(8-15栋)005车位","remark":"","state":7,"owname":"大笨强","tech_phone":"10883170880","est_tech_finishtime":null,"state_desc":"已取消","createtime":"2016-05-23 10:22","owner_phone":"13427525758","est_starttime":null,"techname":"何衣庆","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"","cancel_reason":"不想洗了","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":null,"proname":"釉蜡内外精洗","actStartAndElapsedTime":"","finishtime":"2016-05-23T10:24:19","owner_take_key_time":null,"carnum":"粤TTTTE1","orderid":7325},{"caneceltime":"2016-05-23 10:40","areaname":"凤凰城凤锦苑1街凤锦一街负一层(8-15栋)002车位","remark":"","state":3,"owname":"大笨强","tech_phone":"10427525758","est_tech_finishtime":null,"state_desc":"已取消","createtime":"2016-05-23 09:58","owner_phone":"13427525758","est_starttime":null,"techname":"陈进浩","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"","cancel_reason":"","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":null,"proname":"釉蜡内外精洗","actStartAndElapsedTime":"","finishtime":"2016-05-23T10:40:00","owner_take_key_time":null,"carnum":"粤RRRGTH","orderid":7323},{"caneceltime":"2016-05-22 15:20","areaname":"凤凰城凤锦苑1街凤锦一街负一层(8-15栋)012车位","remark":"","state":3,"owname":"大笨强","tech_phone":"10083170880","est_tech_finishtime":null,"state_desc":"已取消","createtime":"2016-05-22 14:41","owner_phone":"18883170884","est_starttime":null,"techname":"何晓炜","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"","cancel_reason":"","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":null,"proname":"釉蜡外洗","actStartAndElapsedTime":"","finishtime":"2016-05-22T15:20:00","owner_take_key_time":null,"carnum":"赣TTTGT7","orderid":7322},{"caneceltime":"","areaname":"凤凰城凤锦苑1街凤锦一街负一层(8-15栋)012车位","remark":"","state":3,"owname":"大笨强","tech_phone":"10083170880","est_tech_finishtime":null,"state_desc":"已取消","createtime":"2016-05-22 14:41","owner_phone":"18883170884","est_starttime":null,"techname":"何晓炜","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"","cancel_reason":"","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":null,"proname":"釉蜡外洗","actStartAndElapsedTime":"","finishtime":"2016-05-22T00:00:00","owner_take_key_time":null,"carnum":"赣TTTGT7","orderid":7321},{"caneceltime":"2016-05-21 22:31","areaname":"云麓公馆负一层2车位","remark":"","state":7,"owname":"大笨强","tech_phone":"10000000000","est_tech_finishtime":null,"state_desc":"已取消","createtime":"2016-05-21 17:02","owner_phone":"18883170884","est_starttime":null,"techname":"牛海军","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"","cancel_reason":"","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":null,"proname":"釉蜡内外精洗","actStartAndElapsedTime":"","finishtime":"2016-05-21T22:31:01","owner_take_key_time":null,"carnum":"粤RRR112","orderid":7320},{"caneceltime":"2016-05-21 17:01","areaname":"云麓公馆负一层2车位","remark":"","state":7,"owname":"大笨强","tech_phone":"10000000000","est_tech_finishtime":null,"state_desc":"已取消","createtime":"2016-05-21 17:00","owner_phone":"18883170884","est_starttime":null,"techname":"牛海军","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"","cancel_reason":"不想洗了","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":null,"proname":"釉蜡内外精洗","actStartAndElapsedTime":"","finishtime":"2016-05-21T17:01:12","owner_take_key_time":null,"carnum":"粤RRR111","orderid":7318},{"caneceltime":"2016-05-21 17:00","areaname":"云麓公馆负一层2车位","remark":"技师:徐旺旺 帮车主下单","state":7,"owname":"大笨强","tech_phone":"10000000000","est_tech_finishtime":"2016-05-21T18:10:00","state_desc":"已取消","createtime":"2016-05-21 17:00","owner_phone":"18883170884","est_starttime":"2016-05-21T17:00:00","techname":"牛海军","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"17:00施工, 耗时40分钟","cancel_reason":"不想洗了","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":"2016-05-21T19:00:00","proname":"釉蜡外洗","actStartAndElapsedTime":"","finishtime":"2016-05-21T17:00:29","owner_take_key_time":null,"carnum":"粤RRR111","orderid":7317},{"caneceltime":"2016-05-21 16:51","areaname":"云麓公馆负一层2车位","remark":"","state":7,"owname":"大笨强","tech_phone":"10000000000","est_tech_finishtime":"2016-05-21T18:00:00","state_desc":"已取消","createtime":"2016-05-21 16:50","owner_phone":"18883170884","est_starttime":"2016-05-21T16:50:00","techname":"牛海军","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"16:50施工, 耗时40分钟","cancel_reason":"不想洗了","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":"2016-05-21T19:00:00","proname":"釉蜡外洗","actStartAndElapsedTime":"","finishtime":"2016-05-21T16:51:49","owner_take_key_time":null,"carnum":"粤RRR111","orderid":7316},{"caneceltime":"2016-05-23 19:26","areaname":"凤凰城凤锦苑1街凤锦一街负一层(8-15栋)002车位","remark":"","state":7,"owname":"江山笑烟雨遥","tech_phone":"10427525758","est_tech_finishtime":"2016-05-21T20:54:00","state_desc":"已取消","createtime":"2016-05-21 16:43","owner_phone":"18883170884","est_starttime":"2016-05-21T19:44:00","techname":"陈进浩","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"19:44施工, 耗时40分钟","cancel_reason":"其他","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":"2016-05-22T06:00:00","proname":"釉蜡外洗","actStartAndElapsedTime":"","finishtime":"2016-05-23T19:26:51","owner_take_key_time":null,"carnum":"粤C12345","orderid":7315},{"caneceltime":"2016-05-21 16:40","areaname":"凤凰城凤锦苑1街凤锦一街负一层(8-15栋)002车位","remark":"","state":7,"owname":"江山笑烟雨遥","tech_phone":"10427525758","est_tech_finishtime":null,"state_desc":"已取消","createtime":"2016-05-21 16:39","owner_phone":"18883170884","est_starttime":null,"techname":"陈进浩","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"","cancel_reason":"不想洗了","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":null,"proname":"釉蜡内外精洗","actStartAndElapsedTime":"","finishtime":"2016-05-21T16:40:30","owner_take_key_time":null,"carnum":"粤C12378","orderid":7314},{"caneceltime":"2016-05-21 16:34","areaname":"云麓公馆负一层2车位","remark":"技师:陈进浩 帮车主下单","state":7,"owname":"大笨强","tech_phone":"10000000000","est_tech_finishtime":"2016-05-21T17:44:00","state_desc":"已取消","createtime":"2016-05-21 16:33","owner_phone":"18883170884","est_starttime":"2016-05-21T16:34:00","techname":"牛海军","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"16:34施工, 耗时40分钟","cancel_reason":"不想洗了","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":"2016-05-21T19:00:00","proname":"釉蜡外洗","actStartAndElapsedTime":"","finishtime":"2016-05-21T16:34:03","owner_take_key_time":null,"carnum":"粤AABB11","orderid":7313},{"caneceltime":"2016-05-21 16:32","areaname":"云麓公馆负一层2车位","remark":"","state":7,"owname":"大笨强","tech_phone":"10000000000","est_tech_finishtime":null,"state_desc":"已取消","createtime":"2016-05-21 16:31","owner_phone":"18883170884","est_starttime":null,"techname":"牛海军","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"","cancel_reason":"不想洗了","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":null,"proname":"釉蜡内外精洗","actStartAndElapsedTime":"","finishtime":"2016-05-21T16:32:40","owner_take_key_time":null,"carnum":"粤AABB11","orderid":7312},{"caneceltime":"2016-05-21 16:18","areaname":"云麓公馆负一层2车位","remark":"","state":7,"owname":"大笨强","tech_phone":"10831708830","est_tech_finishtime":null,"state_desc":"已取消","createtime":"2016-05-21 16:14","owner_phone":"18883170884","est_starttime":null,"techname":"徐旺旺","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"","cancel_reason":"不想洗了","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":null,"proname":"釉蜡内外精洗","actStartAndElapsedTime":"","finishtime":"2016-05-21T16:18:14","owner_take_key_time":null,"carnum":"粤AABB12","orderid":7310},{"caneceltime":"2016-05-21 16:18","areaname":"云麓公馆负一层2车位","remark":"技师:陈进浩 帮车主下单","state":7,"owname":"大笨强","tech_phone":"10000000000","est_tech_finishtime":"2016-05-21T17:22:00","state_desc":"已取消","createtime":"2016-05-21 16:11","owner_phone":"18883170884","est_starttime":"2016-05-21T16:12:00","techname":"牛海军","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"16:12施工, 耗时40分钟","cancel_reason":"不想洗了","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":"2016-05-21T19:00:00","proname":"釉蜡外洗","actStartAndElapsedTime":"","finishtime":"2016-05-21T16:18:25","owner_take_key_time":null,"carnum":"粤AABB11","orderid":7309},{"caneceltime":"2016-05-21 16:08","areaname":"云麓公馆负一层2车位","remark":"技师:陈进浩 帮车主下单","state":7,"owname":"大笨强","tech_phone":"10000000000","est_tech_finishtime":"2016-05-21T17:18:00","state_desc":"已取消","createtime":"2016-05-21 16:07","owner_phone":"18883170884","est_starttime":"2016-05-21T16:08:00","techname":"牛海军","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"16:08施工, 耗时40分钟","cancel_reason":"不想洗了","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":"2016-05-21T19:00:00","proname":"釉蜡外洗","actStartAndElapsedTime":"","finishtime":"2016-05-21T16:08:48","owner_take_key_time":null,"carnum":"粤AABB11","orderid":7308},{"caneceltime":"2016-05-21 16:07","areaname":"云麓公馆负一层2车位","remark":"技师:陈进浩 帮车主下单","state":7,"owname":"大笨强","tech_phone":"10000000000","est_tech_finishtime":"2016-05-21T16:39:00","state_desc":"已取消","createtime":"2016-05-21 15:28","owner_phone":"18883170884","est_starttime":"2016-05-21T15:29:00","techname":"牛海军","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"15:29施工, 耗时40分钟","cancel_reason":"不想洗了","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":"2016-05-21T19:00:00","proname":"釉蜡外洗","actStartAndElapsedTime":"","finishtime":"2016-05-21T16:07:43","owner_take_key_time":null,"carnum":"粤AABB11","orderid":7307},{"caneceltime":"2016-05-21 15:28","areaname":"云麓公馆负一层2车位","remark":"","state":7,"owname":"大笨强","tech_phone":"10000000000","est_tech_finishtime":"2016-05-21T16:32:00","state_desc":"已取消","createtime":"2016-05-21 15:22","owner_phone":"18883170884","est_starttime":"2016-05-21T15:22:00","techname":"牛海军","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"15:22施工, 耗时40分钟","cancel_reason":"不想洗了","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":"2016-05-21T19:00:00","proname":"釉蜡外洗","actStartAndElapsedTime":"","finishtime":"2016-05-21T15:28:30","owner_take_key_time":null,"carnum":"粤AABB11","orderid":7306},{"caneceltime":"2016-05-21 14:53","areaname":"云麓公馆负一层2车位","remark":"","state":7,"owname":"大笨强","tech_phone":"10000000000","est_tech_finishtime":"2016-05-21T16:10:00","state_desc":"已取消","createtime":"2016-05-21 14:53","owner_phone":"18883170884","est_starttime":"2016-05-21T15:00:00","techname":"牛海军","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"15:00施工, 耗时40分钟","cancel_reason":"不想洗了","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":"2016-05-21T19:00:00","proname":"釉蜡外洗","actStartAndElapsedTime":"","finishtime":"2016-05-21T14:53:53","owner_take_key_time":null,"carnum":"粤C12378","orderid":7305},{"caneceltime":"2016-05-21 14:51","areaname":"凤凰城凤锦苑1街凤锦一街负一层(8-15栋)002车位","remark":"","state":7,"owname":"江山笑烟雨遥","tech_phone":"10427525758","est_tech_finishtime":null,"state_desc":"已取消","createtime":"2016-05-21 14:51","owner_phone":"18883170884","est_starttime":null,"techname":"陈进浩","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"","cancel_reason":"不想洗了","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":null,"proname":"釉蜡内外精洗","actStartAndElapsedTime":"","finishtime":"2016-05-21T14:51:50","owner_take_key_time":null,"carnum":"粤C12378","orderid":7304},{"caneceltime":"2016-05-21 14:39","areaname":"凤凰城凤锦苑1街凤锦一街负一层(8-15栋)002车位","remark":"技师:陈进浩 帮车主下单","state":7,"owname":"大笨强","tech_phone":"10427525758","est_tech_finishtime":"2016-05-21T17:38:00","state_desc":"已取消","createtime":"2016-05-21 14:38","owner_phone":"18883170884","est_starttime":"2016-05-21T16:28:00","techname":"陈进浩","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"16:28施工, 耗时40分钟","cancel_reason":"不想洗了","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":"2016-05-21T19:00:00","proname":"釉蜡外洗","actStartAndElapsedTime":"","finishtime":"2016-05-21T14:39:02","owner_take_key_time":null,"carnum":"粤FFF000","orderid":7301},{"caneceltime":"2016-05-21 14:34","areaname":"凤凰城凤馨苑1街2栋三区负一层22车位","remark":"技师:陈进浩 帮车主下单","state":7,"owname":"大笨强","tech_phone":"10427525758","est_tech_finishtime":"2016-05-21T17:29:00","state_desc":"已取消","createtime":"2016-05-21 14:29","owner_phone":"18883170884","est_starttime":"2016-05-21T16:19:00","techname":"陈进浩","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"16:19施工, 耗时40分钟","cancel_reason":"不想洗了","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":"2016-05-21T19:00:00","proname":"釉蜡外洗","actStartAndElapsedTime":"","finishtime":"2016-05-21T14:34:24","owner_take_key_time":null,"carnum":"粤AAA125","orderid":7300},{"caneceltime":"2016-05-21 14:23","areaname":"凤凰城凤锦苑1街凤锦一街负一层(8-15栋)002车位","remark":"","state":7,"owname":"江山笑烟雨遥","tech_phone":"10883170880","est_tech_finishtime":null,"state_desc":"已取消","createtime":"2016-05-21 14:22","owner_phone":"18883170884","est_starttime":null,"techname":"唐章龙","tech_store_key_time":null,"tech_finishtime":null,"estStartAndElapsedtime":"","cancel_reason":"不想洗了","tech_take_key_time":null,"owner_store_key_time":null,"starttime":null,"est_finishtime":null,"proname":"釉蜡内外精洗","actStartAndElapsedTime":"","finishtime":"2016-05-21T14:23:16","owner_take_key_time":null,"carnum":"粤C12377","orderid":7298}]}
}