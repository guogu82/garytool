package com.gary.garytool.business.map.api.mapsevice;

public class GISConvert {

	/** 双精度浮点型经纬度转到整型经纬度的倍数 */
	public static int GeoToIntScaler = 10000000;

	/** 大中国矩形区域的开始经度 */
	public static  int startLongitudeOfChinaMap = (int) (73.6 * GeoToIntScaler);

	/** 大中国矩形区域的结束经度 */
	public static  int endLongitudeOfChinaMap = (int) (135.4 * GeoToIntScaler);

	/** 大中国矩形区域的开始纬度 */
	public static  int startLatitudeOfChinaMap = (int) (3.8 * GeoToIntScaler);

	/** 大中国矩形区域的结束纬度 */
	public static  int endLatitudeOfChinaMap = (int) (53.56 * GeoToIntScaler);

	// private static  int correctStepFactor = 1;
	/** DOUBLE型经纬度的校正因子 */
	private static  double D_EPSILON = 0.00000001;

	/** map包里图片最大列数 */
	public static  int SnXCountInMap = 100;

	/** map包里图片最大行数 */
	public static  int SnYCountInMap = 100;

	/** map包文件夹里最大列数 */
	public static  int GridMapCols = 10;

	/** map包文件夹里最大行数 */
	public static  int GridMapRows = 10;

	/** 文件分隔符 */
	private static  char SEP =  '/';

	/**
	 * 各缩小级别的步长
	 */
	public static  int[] iStep = { 
                          (int) (GeoToIntScaler * 30) // zoom: 0
			, (int) (GeoToIntScaler * 15) // zoom: 1
			, (int) (GeoToIntScaler * 10) // zoom: 2
			, (int) (GeoToIntScaler * 4) // zoom: 3
			, (int) (GeoToIntScaler * 2) // zoom: 4
			, (int) (GeoToIntScaler * 1) // zoom: 5
			, (int) (GeoToIntScaler * 0.4) // zoom: 6
			, (int) (GeoToIntScaler * 0.2) // zoom: 7
			, (int) (GeoToIntScaler * 0.1) // zoom: 8
			, (int) (GeoToIntScaler * 0.04) // zoom: 9
			, (int) (GeoToIntScaler * 0.02) // zoom: 10
			, (int) (GeoToIntScaler * 0.01) // zoom: 11
			, (int) (GeoToIntScaler * 0.004) // zoom: 12
			, (int) (GeoToIntScaler * 0.002) // zoom: 13
			, (int) (GeoToIntScaler * 0.001) // zoom: 14
			, (int) (GeoToIntScaler * 0.0004) // zoom: 15
	};

	// public static  int[] iStep1 =
	// {
	// (int)(10000 * 30 ) // zoom: 0
	// ,(int)(10000 * 15 ) // zoom: 1
	// ,(int)(10000 * 10 ) // zoom: 2
	// ,(int)(10000 * 4 ) // zoom: 3
	// ,(int)(10000 * 2 ) // zoom: 4
	// ,(int)(10000 * 1 ) // zoom: 5
	// ,(int)(10000 * 0.4 ) // zoom: 6
	// ,(int)(10000 * 0.2 ) // zoom: 7
	// ,(int)(10000 * 0.1 ) // zoom: 8
	// ,(int)(10000 * 0.04 ) // zoom: 9
	// ,(int)(10000 * 0.02 ) // zoom: 10
	// ,(int)(10000 * 0.01 ) // zoom: 11
	// ,(int)(10000 * 0.004 ) // zoom: 12
	// ,(int)(10000 * 0.002 ) // zoom: 13
	// ,(int)(10000 * 0.001 ) // zoom: 14
	// ,(int)(10000 * 0.0004) // zoom: 15
	// };
	/**
	 * /// 在每个行  网格目录  里的文件基数 -- 在当前经纬度范围里的PNG图的存放目录 ///
	 * 如基数为10，即该文件夹里最大存储文件数为10*10=100个文件 ///
	 */
	public static  int[] iGrid = {
            10, // zoom: 0
			10, // zoom: 1
			10, // zoom: 2
			10, // zoom: 3
			10, // zoom: 4
			10, // zoom: 5
			10, // zoom: 6
			10, // zoom: 7
			10, // zoom: 8
			10, // zoom: 9
			40, // zoom: 10
			40, // zoom: 11
			40, // zoom: 12
			40, // zoom: 13
			40, // zoom: 14
			40  // zoom: 15
	};

	/**
	 * 通过经度或纬度转换成网格号，用于256*256图
	 * 
	 * @param gis
	 *            经度或纬度
	 * @param level
	 *            级别
	 * @return 网格号
	 */
	public static int gisToSn_Web(double gis, int level) {
		int value = (int) ((gis + D_EPSILON) * GeoToIntScaler);
		return gisToSn_Web(value, level);
	}

	/**
	 * 通过经度或纬度转换成网格号，用于256*256图
	 * 
	 * @param gis
	 *            经度或纬度
	 * @param level
	 *            级别
	 * @return 网格号
	 */
	public static int gisToSn_Web(int gis, int level) {
		return gis / iStep[level];
	}

	/**
	 * 通过经度或纬度转换成网格号，用于128*128图
	 * 
	 * @param gis
	 *            经度或纬度
	 * @param level
	 *            级别
	 * @return 网格号
	 */
	public static int gisToSn_Mobile(double gis, int level) {
		int value = (int) ((gis + D_EPSILON) * GeoToIntScaler);
		return gisToSn_Mobile(value, level);
	}

	/**
	 * 通过经度或纬度转换成网格号，用于128*128图
	 * 
	 * @param gis
	 *            经度或纬度
	 * @param level
	 *            级别
	 * @return 网格号
	 */
	public static int gisToSn_Mobile(int gis, int level) {
		return gis / (iStep[level] / 2);
		// long g = (long)gis*2;
		// int result = (int)(g/iStep[level]);
		// return result;
		// return gis*2 / iStep[level];
	}

	/**
	 * 图片号转成经度或纬度
	 * 
	 * @param sn
	 * @param level
	 * @return
	 */
	public static int snWebToStartLonLatInt32(int sn, int level) {
		return sn * iStep[level];
	}

	/**
	 * 移动设备图片号转成经度或纬度
	 * 
	 * @param sn
	 * @param level
	 * @return
	 */
	public static int snMobileToStartLonLatInt32(int sn, int level) {
		return sn / 2 * iStep[level];
	}

	/**
	 * 将双精度的经纬度转换成整型的经纬度
	 * 
	 * @param value
	 *            双精度的经纬度
	 * @return 整型的经纬度
	 */
	public static int lonlatDoubleToInt32(double value) {
		return (int) ((value + D_EPSILON) * GeoToIntScaler);
	}

	/**
	 * 通过经纬度和级别，返回对应PNG图片的相对存储路径
	 * 
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param level
	 *            级别
	 * @return 对应PNG图片的相对存储路径
	 */
	public static String getPNGPath(double longitude, double latitude, int level) {
		// int lon = (int) ((longitude + D_EPSILON) * GeoToIntScaler);
		// int lat = (int) ((latitude + D_EPSILON) * GeoToIntScaler);
		// return getPNGPath(lon, lat, level);
		int snX = gisToSn_Web(longitude, level);
		int snY = gisToSn_Web(latitude, level);
		return getPNGPathBySn(snX, snY, level);
	}

	/**
	 * 通过经纬度和级别，返回对应PNG图片的相对存储路径
	 * 
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param level
	 *            级别
	 * @return 对应PNG图片的相对存储路径
	 */
	public static String getPNGPath(int longitude, int latitude, int level) {
		// longitude = longitude / iStep[level];
		// latitude = latitude / iStep[level];
		//
		// int grid = iGrid[level];
		// String result = "\\" + level + "\\" +
		// (longitude / grid) + "\\" + (latitude / grid) + "\\" + (longitude %
		// grid) + "_" + (latitude % grid) + ".png";
		// return result;
		int snX = gisToSn_Web(longitude, level);
		int snY = gisToSn_Web(latitude, level);
		return getPNGPathBySn(snX, snY, level);
	}

	/**
	 * 通过经纬度和级别，返回对应PNG图片的相对存储路径
	 * 
	 * @param snX
	 *            snX
	 * @param snY
	 *            snY
	 * @param level
	 *            级别
	 * @return 对应PNG图片的相对存储路径
	 */
	public static String getPNGPathBySn(int snX, int snY, int level) {
		int grid = iGrid[level];

        StringBuilder sb = new StringBuilder();
        sb.append("/").append(level)
                .append("/").append((snX / grid))
                .append("/").append((snY / grid))
                .append("/").append((snX % grid))
                .append("_").append((snY % grid))
                .append(".png");

//		String result = "/" + level + "/" + (snX / grid) + "/" + (snY / grid)
//				+ "/" + (snX % grid) + "_" + (snY % grid) + ".png";
//		return result;
        
        return sb.toString();


	}

	/**
	 * 通过经纬度和级别，返回服务器MAP包的相对存储路径
	 * 
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param level
	 *            级别
	 * @return 服务器MAP包的相对存储路径
	 */
	public static String getServerMapPath(double longitude, double latitude,
			int level) {
		int snX = gisToSn_Web(longitude, level);
		int snY = gisToSn_Web(latitude, level);
		// System.out.println("aaaa:" + snX + " " + snY);
		return getServerMapPathBySn(snX, snY, level);
	}

	/**
	 * 通过经纬度和级别，返回服务器MAP包的相对存储路径
	 * 
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param level
	 *            级别
	 * @return 服务器MAP包的相对存储路径
	 */
	public static String getServerMapPath(int longitude, int latitude, int level) {
		int snX = gisToSn_Web(longitude, level);
		int snY = gisToSn_Web(latitude, level);
		return getServerMapPathBySn(snX, snY, level);
	}

	/**
	 * 通过图片号和级别，返回服务器MAP包的相对存储路径
	 * 
	 * @param snX
	 *            snX
	 * @param snY
	 *            snY
	 * @param level
	 *            级别
	 * @return 服务器MAP包的相对存储路径
	 */
	public static String getServerMapPathBySn(int snX, int snY, int level) {
		int colDir = snX / SnXCountInMap / GridMapCols;
		int rowDir = snY / SnYCountInMap / GridMapRows;
		int ci = (snX % (SnXCountInMap * GridMapCols)) / SnXCountInMap;
		int ri = (snY % (SnYCountInMap * GridMapRows)) / SnYCountInMap;
		String strLevel = level < 10 ? ("0" + level) : level + "";
        String strCi = ci < 10 ? ("0" + ci) : ci + "";
        String strRi = ri < 10 ? ("0" + ri) : ri + "";

//        String result = SEP + strLevel + SEP + colDir + SEP + rowDir + SEP
//				+ strCi + "_" + strRi + ".map";

        StringBuilder result = new StringBuilder();
		result.append(SEP).append(strLevel).append(SEP).append(colDir).append(
				SEP).append(rowDir).append(SEP).append(strCi).append("_")
				.append(strRi).append(".map");
        
        return result.toString();



	}

	/**
	 * 通过经纬度和级别，返回服务器MAP包的相对存储路径
	 * 
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param level
	 *            级别
	 * @return 服务器MAP包的相对存储路径
	 */
	public static String getMobileServerMapPath(double longitude,
			double latitude, int level) {
		int snX = gisToSn_Mobile(longitude, level);
		int snY = gisToSn_Mobile(latitude, level);

		return getMobileServerMapPathBySn(snX, snY, level);
	}

	/**
	 * 通过经纬度和级别，返回服务器MAP包的相对存储路径
	 * 
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param level
	 *            级别
	 * @return 服务器MAP包的相对存储路径
	 */
	public static String getMobileServerMapPath(int longitude, int latitude,
			int level) {
		int snX = gisToSn_Mobile(longitude, level);
		int snY = gisToSn_Mobile(latitude, level);
		return getMobileServerMapPathBySn(snX, snY, level);
	}

	/**
	 * 通过图片号和级别，返回服务器MAP包的相对存储路径
	 * 
	 * @param snX
	 *            snX
	 * @param snY
	 *            snY
	 * @param level
	 *            级别
	 * @return 服务器MAP包的相对存储路径
	 */
	public static String getMobileServerMapPathBySn(int snX, int snY, int level) {
		// System.out.println("path snX:"+snX);
		// System.out.println("path snX:"+snY);
		snX = snX / 2;
		snY = snY / 2;
		// System.out.println("path snX:"+snX);
		// System.out.println("path snX:"+snY);
		int colDir = snX / SnXCountInMap / GridMapCols;
		int rowDir = snY / SnYCountInMap / GridMapRows;
		int ci = (snX % (SnXCountInMap * GridMapCols)) / SnXCountInMap;
		int ri = (snY % (SnYCountInMap * GridMapRows)) / SnYCountInMap;

        StringBuilder result = new StringBuilder();
		String strLevel = level < 10 ? ("0" + level) : level + "";
		String strCi = ci < 10 ? ("0" + ci) : ci + "";
		String strRi = ri < 10 ? ("0" + ri) : ri + "";
		result.append(SEP).append(strLevel).append(SEP).append(colDir).append(
				SEP).append(rowDir).append(SEP).append(strCi).append("_")
				.append(strRi).append(".map");
		// String result = SEP + strLevel + SEP + colDir + SEP + rowDir + SEP +
		// strCi + "_" + strRi + ".map";
		return result.toString();
	}

    ///**
    // * 根据文件路径返回移动设备地图包信息量<BR/> 级别、开始snX、结束snX、开始snY、结束snY
    // * 
    // * @param path
    // * @return
    // */
    //public static int[] getWebMapInfoFromPath(String path) {
    //    // System.out.println("getWebMapInfoFromPath path: " + path);
    //    int[] result = null;
    //    String[] pathPart = path.Split("\\\\");
    //    int len = pathPart.Length;
    //    int level = Int32.Parse(pathPart[len - 4]);
    //    int snX = Int32.Parse(pathPart[len - 3]) * SnXCountInMap
    //            * GridMapCols;
    //    int snY = Int32.Parse(pathPart[len - 2]) * SnYCountInMap
    //            * GridMapRows;
    //    String fileName = pathPart[len - 1];
    //    fileName = fileName.Split("\\.")[0];
    //    pathPart = fileName.Split('_');
    //    snX = snX + Int32.Parse(pathPart[0]) * SnXCountInMap;
    //    snY = snY + Int32.Parse(pathPart[1]) * SnYCountInMap;

    //    // snX *= 2;
    //    // snY *= 2;
    //    result = new int[5];
    //    result[0] = level;
    //    result[1] = snX;
    //    result[2] = snY;
    //    result[3] = snX + SnXCountInMap/** 2 */
    //    - 1;
    //    result[4] = snY + SnYCountInMap/** 2 */
    //    - 1;
    //    return result;
    //}

    ///**
    // * 根据文件路径返回移动设备地图包信息量<BR/> 级别、开始snX、结束snX、开始snY、结束snY
    // * 
    // * @param path
    // * @return
    // */
    //public static int[] getMobileMapInfoFromPath(String path) {
    //    // System.out.println("getMobileMapInfoFromPath path: " + path);
    //    int[] result = null;
    //    String[] pathPart = path.Split("\\\\");
    //    int len = pathPart.Length;
    //    int level = Int32.Parse(pathPart[len - 4]);
    //    int snX = Int32.Parse(pathPart[len - 3]) * SnXCountInMap
    //            * GridMapCols;
    //    int snY = Int32.Parse(pathPart[len - 2]) * SnYCountInMap
    //            * GridMapRows;
    //    String fileName = pathPart[len - 1];
    //    fileName = fileName.Split("\\.")[0];
    //    pathPart = fileName.Split('_');
    //    snX = snX + Int32.Parse(pathPart[0]) * SnXCountInMap;
    //    snY = snY + Int32.Parse(pathPart[1]) * SnYCountInMap;

    //    snX *= 2;
    //    snY *= 2;
    //    result = new int[5];
    //    result[0] = level;
    //    result[1] = snX;
    //    result[2] = snY;
    //    result[3] = snX + SnXCountInMap * 2 - 1;
    //    result[4] = snY + SnYCountInMap * 2 - 1;
    //    return result;
    //}

	/**
	 * 根据经纬度，返回对应所在的服务器MAP包图片号范围<BR/> [0]开始snX，[1]开始snY，[2]结束snX，[3]结束snY
	 * 
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param level
	 *            级别
	 * @return 服务器MAP包图片号范围。<BR/>[0]开始snX，[1]开始snY，[2]结束snX，[3]结束snY
	 */
	public static int[] getMapSnRegionByLonLat(double longitude,
			double latitude, int level) {
		return getMapSnRegionByLonLat(lonlatDoubleToInt32(longitude),
				lonlatDoubleToInt32(latitude), level);
	}

	/**
	 * 根据经纬度，返回对应所在的服务器MAP包图片号范围<BR/> [0]开始snX，[1]开始snY，[2]结束snX，[3]结束snY
	 * 
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param level
	 *            级别
	 * @return 服务器MAP包图片号范围。<BR/>[0]开始snX，[1]开始snY，[2]结束snX，[3]结束snY
	 */
	public static int[] getMapSnRegionByLonLat(int longitude, int latitude,
			int level) {
		int snX = gisToSn_Web(longitude, level);
		int snY = gisToSn_Web(latitude, level);
		return getMapSnRegionBySn(snX, snY);
	}

	/**
	 * 根据图片号，返回对应所在的服务器MAP包图片号范围<BR/> [0]开始snX，[1]开始snY，[2]结束snX，[3]结束snY
	 * 
	 * @param snX
	 *            snX
	 * @param snY
	 *            snY
	 * @return 服务器MAP包图片号范围。[0]开始snX，[1]开始snY，[2]结束snX，[3]结束snY
	 */
	public static int[] getMapSnRegionBySn(int snX, int snY) {
		int[] result = new int[4];
		result[0] = snX - snX % SnXCountInMap;
		result[1] = snY - snY % SnYCountInMap;
		result[2] = result[0] + SnXCountInMap - 1;
		result[3] = result[1] + SnYCountInMap - 1;
		return result;
	}

	/**
	 * 根据图片号，返回对应所在的服务器MAP包图片号范围<BR/> [0]开始snX，[1]开始snY，[2]结束snX，[3]结束snY
	 * 
	 * @param snX
	 *            snX
	 * @param snY
	 *            snY
	 * @return 服务器MAP包图片号范围。[0]开始snX，[1]开始snY，[2]结束snX，[3]结束snY
	 */
	public static int[] getMobileMapSnRegionBySn(int snX, int snY) {
		int snXCount = SnXCountInMap * 2;
		int snYCount = SnYCountInMap * 2;
		int[] result = new int[4];
		result[0] = snX - snX % snXCount;
		result[1] = snY - snY % snYCount;
		result[2] = result[0] + snXCount - 1;
		result[3] = result[1] + snYCount - 1;
		return result;
	}

	/**
	 * 根据经纬度和级别返回所在MAP的经纬度范围
	 * 
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param level
	 *            级别
	 * @return MAP的经纬度范围
	 */
	public static double[] getMapLonLatRegionByLonLat(double longitude,
			double latitude, int level) {
		int[] tmp = getMapLonLatRegionByLonLat(lonlatDoubleToInt32(longitude),
				lonlatDoubleToInt32(latitude), level);
		double[] result = new double[tmp.length];
		for (int i = 0, len = result.length; i < len; i++) {
			result[i] = (double) tmp[i] / GeoToIntScaler;
		}
		return result;
	}

	/**
	 * 根据经纬度和级别返回所在MAP的经纬度范围
	 * 
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param level
	 *            级别
	 * @return MAP的经纬度范围
	 */
	public static int[] getMapLonLatRegionByLonLat(int longitude, int latitude,
			int level) {
		int[] result = new int[4];
		return result;
	}

	/**
	 * 服务器地图包的经度宽度
	 * 
	 * @param level
	 *            级别
	 * @return
	 */
	public static double getMapLongitudeWidthDouble(int level) {
		return (double) getMapLongitudeWidthInt32(level) / GeoToIntScaler;
	}

	/**
	 * 服务器地图包的经度宽度
	 * 
	 * @param level
	 *            级别
	 * @return
	 */
	public static int getMapLongitudeWidthInt32(int level) {
		return iStep[level] * SnXCountInMap;
	}

	/**
	 * 服务器地图包的纬度高度
	 * 
	 * @param level
	 *            级别
	 * @return
	 */
	public static double getMapLatitudeHeightDouble(int level) {
		return (double) getMapLatitudeHeightInt32(level) / GeoToIntScaler;
	}

	/**
	 * 服务器地图包的纬度高度
	 * 
	 * @param level
	 * @return 级别
	 */
	public static int getMapLatitudeHeightInt32(int level) {
		return iStep[level] * SnYCountInMap;
	}
}
