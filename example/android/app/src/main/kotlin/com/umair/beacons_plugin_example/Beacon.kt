package com.umair.beacons_plugin_example

data class Beacon(
        var name: String? = "",
        var uuid: String? = "",
        var major: String = "",
        var minor: String = "",
        var distance: String = "",
        var proximity: String = ""
) {

    override fun toString(): String {
        return "{\n" +
                "  \"name\": \"$name\",\n" +
                "  \"uuid\": \"$uuid\",\n" +
                "  \"major\": \"$major\",\n" +
                "  \"minor\": \"$minor\",\n" +
                "  \"distance\": \"$distance\",\n" +
                "  \"proximity\": \"$proximity\"\n" +
                "}"
    }

    companion object {
        fun getProximityOfBeacon(beacon: org.altbeacon.beacon.Beacon): Proximity {
            return if (beacon.distance < 0.5) {
                Proximity.IMMEDIATE
            } else if (beacon.distance > 0.5 && beacon.distance < 3.0) {
                Proximity.NEAR
            } else if (beacon.distance > 3.0) {
                Proximity.FAR
            } else {
                Proximity.UNKNOWN
            }
        }
    }
}