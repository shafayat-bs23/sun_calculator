package com.example.bs217.suncalculation

import java.lang.Math.*
import java.util.*
import kotlin.math.sin

fun getDayFromEpoch(epoch:Long){
    var calender : Calendar = Calendar.getInstance()
    calender.timeInMillis = epoch
    print(calender.time)
}


fun getJdFromEpoch(epoch:Double) : Double{

    return (epoch / 86400.0) + 2440587.5
}

fun getJcFromJd(jd:Double) : Double{

    return (jd - 2451545.0) / 36525.0
}

fun getGeomMeanLongitudeOfSunInRadian(jc:Double) : Double{
    val operand1 : Double = 280.46646 + jc * (36000.76983 + jc * 0.0003032)
    val operand2 : Double = 360.0

    return toRadians(operand1 % operand2)
}

fun getGeomMeanAnomalyInRadian(jc:Double) : Double{
    return toRadians(357.52911 + jc * (35999.05029 - 0.0001537 * jc))
}

fun getEccentEarthOrbit(jc:Double) : Double{
    return 0.016708634 - jc * (0.000042037 + 0.0000001267 * jc)
}

fun getSunEquationOfCenter(jc:Double) : Double{
    val gmas : Double = getGeomMeanAnomalyInRadian(jc)
    return toRadians(sin(gmas) * (1.914602 - jc * (0.004817 + 0.000014 * jc)) +
    sin(2 * gmas) * (0.019993 - 0.000101 * jc) +
    sin(3 * gmas) * 0.000289)
}

fun getSunTrueLongitudeInRadian(jc:Double) : Double{

    return getGeomMeanLongitudeOfSunInRadian(jc) + getSunEquationOfCenter(jc)
}

fun getSunTrueAnomalyInRadian(jc:Double) : Double{
    return getGeomMeanAnomalyInRadian(jc) + getSunEquationOfCenter(jc)
}

fun getSunApparentLongitudeInRedian(jc:Double) : Double{
    val stl : Double = getSunTrueLongitudeInRadian(jc)
    return toRadians(toDegrees(stl) - 0.00569 - 0.00478 * sin(toRadians(125.04 - 1934.136 * jc)))
}

fun getMeanEclipticObliquityInRadian(jc:Double) : Double{
    return toRadians(23.0 + (26.0 + (21.448 - jc * (46.815 + jc * (0.00059 - jc * 0.001813)))/60.0) / 60.0)
}

fun getObliquityCorrectionInRadian(jc:Double) : Double {
    val meo : Double = getMeanEclipticObliquityInRadian(jc)
    val omega : Double = 125.04 - jc * 1934.136
    return toRadians(toDegrees(meo) + 0.00256 * cos(toRadians(omega)))
}

fun getSunRightAscensionInRadian(jc:Double) : Double{
    val sal : Double = getSunApparentLongitudeInRedian(jc)
    val oc : Double = getObliquityCorrectionInRadian(jc)
    return atan2(cos(sal), cos(oc)) * sin(sal)
}

fun getSunDeclinationInRadian(jc:Double) : Double{
    val oc : Double = getObliquityCorrectionInRadian(jc)
    val sal : Double = getSunApparentLongitudeInRedian(jc)
    return asin(sin(oc) * sin(sal))
}

fun getVaryingEffect(jc:Double) : Double{
    val oc: Double = getObliquityCorrectionInRadian(jc)
    return tan(oc/2.0) * tan(oc/2)
}

fun getEquiationOfTimeInSecond(jc : Double) : Double{
    val ve : Double = getVaryingEffect(jc) //u
    val gmls : Double = getGeomMeanLongitudeOfSunInRadian(jc) //i
    val eeo : Double = getEccentEarthOrbit(jc) //k
    val gmas : Double = getGeomMeanAnomalyInRadian(jc) //j
    return 4 * toDegrees(ve * sin(2*gmls) - 2 * eeo * sin(gmas) + 4 * eeo * ve
    * sin(gmas) * cos(2*gmls) - 0.5 * ve * ve * sin(4*gmls) - 1.25 * eeo * eeo
    * sin(2*gmas)) * 60
}

fun getSunriseHourAngleInRadian(jc:Double, longitude:Double) : Double{
    return acos(cos(toRadians(90.833))/(cos(toRadians(longitude)) * cos(getSunDeclinationInRadian(jc))) - tan(toRadians(longitude)) * tan(getSunDeclinationInRadian(jc)))
}

fun getSolarNoonInSecond(jc:Double, longitude:Double) : Double{
    val eot : Double = getEquiationOfTimeInSecond(jc) / 60
    return (720 - 4 * longitude - eot) * 60
}

fun getSunriseTimeInSecond(jc:Double, longitude: Double) : Double{
    val sn : Double = getSolarNoonInSecond(jc, longitude)
    val sha : Double = toDegrees(getSunriseHourAngleInRadian(jc, longitude))
    return sn - sha*4
}

fun getSunsetTimeInSecond(jc: Double, longitude: Double) : Double{
    val sn : Double = getSolarNoonInSecond(jc, longitude)
    val sha : Double = toDegrees(getSunriseHourAngleInRadian(jc, longitude))
    return sn + sha*4
}

fun getTrueSolarTime(){

}

fun main(args: Array<String>) {
    getDayFromEpoch(Calendar.getInstance().timeInMillis)
}
