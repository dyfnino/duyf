package com.samton.util;

import com.samton.constant.Fields;
import msg.*;
import org.bson.Document;

/**
 * Created with IntelliJ IDEA.
 * 浣滆�: duyingfeng
 * 鏃ユ湡: 17-9-7
 * 璇存槑:
 * To change this template use File | Settings | File Templates.
 */
public class DocumentCommun {

    public static Document GisVo2Doc(TSPGpsBasicInfoVo vo){
        String vinCode = vo.getVinCodee();
        String uuid = vo.getUuiduuid();
        String termCode = vo.getTermCode();
        String latitudes = vo.getLatitude();
        String longtudes = vo.getLongtude();
        Double speedsss = vo.getSpeedsss();
        Long timeStam = vo.getTimeStam();
        Double altitude = vo.getAltitude();
        Integer director = vo.getDirector();
        Integer satellit = vo.getSatellit();

        Long tempOnes = vo.getTempOnes();
        Long tempTwos = vo.getTempTwos();
        Long tempThre = vo.getTempThre();
        String netStand = vo.getNetStand();

        Integer signalLv = vo.getSignalLv();
        Integer wifiStat =  vo.getWifiStat();

        String areaCode = vo.getAreaCode();
        Double displace = vo.getDisplace();

        Document doc = new Document();
        doc.put(Fields.VINCODE, vinCode);
        doc.put(Fields.UUID, uuid);
        doc.put(Fields.TERMCODE, termCode);
        //褰撳墠鏃堕棿 鏃堕棿鎴�
        doc.put(Fields.TIMESTAM, timeStam);
        doc.put(Fields.SPEEDSSS, speedsss);

        doc.put(Fields.LATITUDE, latitudes);
        doc.put(Fields.LONGTUDE, longtudes);
        doc.put(Fields.ALTITUDE, altitude);

        doc.put(Fields.DIRECTOR, director);
        doc.put(Fields.GISDATAE_SATELLITT, satellit);
        doc.put(Fields.GISDATAE_TEMPONES, tempOnes);
        doc.put(Fields.GISDATAE_TEMPTWOS, tempTwos);
        doc.put(Fields.GISDATAE_TEMPTHRE, tempThre);
        doc.put(Fields.GISDATAE_NETSTAND, netStand);
        doc.put(Fields.GISDATAE_SIGNALLV, signalLv);
        doc.put(Fields.GISDATAE_WIFISTAT, wifiStat);

        doc.put(Fields.GISDATAE_AREACODE, areaCode);
        doc.put(Fields.GISDATAE_DISPLACE, displace);


        return doc;
    }

    public static Document getWakeup2Doc(TSPWaitWakeUp tsp){
        //寰呮満鑷敜閱�
        Document doc = new Document();
        //vinCode
        String vinCodee = tsp.getVinCodee();
        //鐔勭伀鏃堕暱
        Double flameout = tsp.getFlameout();
        //绾害
        String lat = tsp.getLatitude();
        //缁忓害
        String lon = tsp.getLongtude();
        //寰呮満鑷敜閱掓椂闂�
        Long rruntime = tsp.getRruntime();
        //termCode
        String termCode = tsp.getTermCode();
        //uuid
        String uuid = tsp.getUuiduuid();
        //鐢靛帇
        Double voltagee = tsp.getVoltagee();


        doc.put(Fields.VINCODE, vinCodee);
        doc.put(Fields.UUID, uuid);

        doc.put(Fields.TERMCODE, termCode);

        doc.put(Fields.WEAKUP_RRUNTIME, rruntime);

        doc.put(Fields.LATITUDE, lat);

        doc.put(Fields.LONGTUDE, lon);

        doc.put(Fields.VOLTAGEE, voltagee);

        doc.put(Fields.WEAKUP_FLAMEOUT, flameout);

        return doc;
    }

    public static Document getSpeedUp2Doc(TSPSpeedUp tsp){
        Document doc = new Document();
        // 鎬ュ姞閫熶簨浠�
        Integer drivType = tsp.getDrivType();
        String termCode = tsp.getTermCode();
        Long timestam = tsp.getTimeStam();
        String vinCode = tsp.getVinCodee();
        String uuid = tsp.getUuiduuid();
        Double urgentAge = tsp.getUrgentAg();

        doc.put(Fields.VINCODE, vinCode);
        doc.put(Fields.TERMCODE, termCode);
        doc.put(Fields.TIMESTAM, timestam);
        doc.put(Fields.DRIVINGHABIT_DRIVTYPE, drivType);
        doc.put(Fields.UUID, uuid);
        doc.put(Fields.DRIVINGHABIT_URGENTAGE, urgentAge);

        return doc;
    }

    public static Document getSpeeddown2Doc(TSPSharpSlowBown tsp){
        Document doc = new Document();
        // 鎬ュ噺閫熶簨浠�
        Integer drivType = tsp.getDrivType();
        Double sharpAge = tsp.getSharpAge();
        String termCode = tsp.getTermCode();
        Long timestam = tsp.getTimeStam();
        String uuid = tsp.getUuiduuid();
        String vinCode = tsp.getVinCodee();
        doc.put(Fields.VINCODE, vinCode);
        doc.put(Fields.TERMCODE, termCode);
        doc.put(Fields.TIMESTAM, timestam);
        doc.put(Fields.DRIVINGHABIT_DRIVTYPE, drivType);
        doc.put(Fields.UUID,uuid);
        doc.put(Fields.DRIVINGHABIT_SHARPAGE, sharpAge);

        return doc;
    }

    public static Document getSharpBend2Doc(TSPSharpBend tsp){
        // 鎬ヨ浆寮簨浠�
        Document doc = new Document();
        Long angulVel = tsp.getAngulVel();
        Integer direTurn = tsp.getDireTurn();
        Integer drivType = tsp.getDrivType();
        Double laterAge = tsp.getLaterAge();
        String termCode = tsp.getTermCode();
        Long timestam = tsp.getTimeStam();
        String uuid = tsp.getUuiduuid();
        String vinCode = tsp.getVinCodee();

        doc.put(Fields.VINCODE, vinCode);
        doc.put(Fields.TERMCODE, termCode);
        doc.put(Fields.TIMESTAM, timestam);
        doc.put(Fields.DRIVINGHABIT_DRIVTYPE, drivType);
        doc.put(Fields.UUID,uuid);
        doc.put(Fields.DRIVINGHABIT_DIRETURN, direTurn);
        doc.put(Fields.DRIVINGHABIT_ANGULVEL, angulVel);
        doc.put(Fields.DRIVINGHABIT_LATERAGE, laterAge);

        return doc;
    }


    public static Document FatigueDriving2Doc(TSPFatigueDriving tsp){
        Document doc = new Document();
        // 鐤插姵椹鹃┒浜嬩欢

        Integer drivType = tsp.getDrivType();
        String termCode = tsp.getTermCode();
        Long timeStam = tsp.getTimeStam();
        Double totLengt = tsp.getTotLengt();
        String uuiduuid = tsp.getUuiduuid();
        String vinCodee = tsp.getVinCodee();

        doc.put(Fields.VINCODE, vinCodee);
        doc.put(Fields.TERMCODE, termCode);
        doc.put(Fields.TIMESTAM, timeStam);
        doc.put(Fields.UUID, uuiduuid);
        doc.put(Fields.DRIVINGHABIT_DRIVTYPE, drivType);

        doc.put(Fields.DRIVINGHABIT_TOTLENGT, totLengt);
        return doc;
    }



    public static Document CallPolice2Doc(TSPCallPolice tsp){
        Document doc = new Document();
        // 瓒呴�鎶ヨ

        Integer alarmTyp = tsp.getAlarmTyp();
        String latitude = tsp.getLatitude();
        String longtude = tsp.getLongtude();
        Double speed = tsp.getSpeedsss();
        String termCode = tsp.getTermCode();
        String vinCodee = tsp.getVinCodee();
        String uuiduuid = tsp.getUuiduuid();
        Long timeStam = tsp.getTimeStam();

        doc.put(Fields.UUID, uuiduuid);
        doc.put(Fields.VINCODE, vinCodee);
        doc.put(Fields.TERMCODE, termCode);
        doc.put(Fields.TIMESTAM, timeStam);
        doc.put(Fields.LATITUDE, latitude);
        doc.put(Fields.LONGTUDE, longtude);
        doc.put(Fields.ALARMINFORMATION_ALARMTYP, alarmTyp);
        doc.put(Fields.SPEEDSSS, speed);
        return doc;
    }


    public static Document ContinuousDriving2Doc(TSPContinuousDriving tsp){
        Document doc = new Document();
        //
        Integer alarmTyp = tsp.getAlarmTyp();
        String latitude = tsp.getLatitude();
        String longtude = tsp.getLongtude();
        String termCode = tsp.getTermCode();
        String uuiduuid = tsp.getUuiduuid();
        String vinCodee = tsp.getVinCodee();
        Long timeStam = tsp.getTimeStam();

        doc.put(Fields.UUID, uuiduuid);
        doc.put(Fields.VINCODE, vinCodee);
        doc.put(Fields.TERMCODE, termCode);
        doc.put(Fields.TIMESTAM, timeStam);
        doc.put(Fields.LATITUDE, latitude);
        doc.put(Fields.LONGTUDE, longtude);
        doc.put(Fields.ALARMINFORMATION_ALARMTYP, alarmTyp);
        return doc;
    }

    public static Document Fence2Doc(TSPFence tsp){
        Document doc = new Document();
        // 鐢靛瓙鍥存爮
        Integer alarmTyp = tsp.getAlarmTyp();
        String longtude = tsp.getLongtude();
        String termCode = tsp.getTermCode();
        Long timeStam = tsp.getTimeStam();
        String uuiduuid = tsp.getUuiduuid();
        String vinCodee = tsp.getVinCodee();
        String latitude = tsp.getLatitude();

        doc.put(Fields.UUID, uuiduuid);
        doc.put(Fields.VINCODE, vinCodee);
        doc.put(Fields.TERMCODE, termCode);
        doc.put(Fields.TIMESTAM, timeStam);
        doc.put(Fields.LATITUDE, latitude);
        doc.put(Fields.LONGTUDE, longtude);
        doc.put(Fields.ALARMINFORMATION_ALARMTYP, alarmTyp);
        return doc;
    }

    public static Document LdleCallPolice2Doc(TSPLdleCallPolice tsp){
        Document doc = new Document();
        // 鎬犻�鎶ヨ
        Integer alarmTyp = tsp.getAlarmTyp();
        String latitude = tsp.getLatitude();
        String longtude = tsp.getLongtude();
        String termCode = tsp.getTermCode();
        Double longTime = tsp.getLongTime();
        Long timeStam = tsp.getTimeStam();
        String vinCodee = tsp.getVinCodee();
        String uuiduuid = tsp.getUuiduuid();

        doc.put(Fields.UUID, uuiduuid);
        doc.put(Fields.VINCODE, vinCodee);
        doc.put(Fields.TERMCODE, termCode);
        doc.put(Fields.TIMESTAM, timeStam);
        doc.put(Fields.LATITUDE, latitude);
        doc.put(Fields.LONGTUDE, longtude);
        doc.put(Fields.ALARMINFORMATION_LONGTIME, longTime);
        doc.put(Fields.ALARMINFORMATION_ALARMTYP, alarmTyp);

        return doc;
    }



}
