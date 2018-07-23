package com.example.bs217.suncalculation

import java.lang.Math.*
import kotlin.math.sin

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

fun getEccentEarchOrbit(jc:Double) : Double{
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

fun sunDeclinationInRadian(jc:Double) : Double{
    val oc : Double = getObliquityCorrectionInRadian(jc)
    val sal : Double = getSunApparentLongitudeInRedian(jc)
    return asin(sin(oc) * sin(sal))
}
