package com.example.shoppingcenternavigator

data class Coordinate(val x:Float, val y: Float)
data class Point(val Floor: Int, val x:Float, val y: Float){
    val connections: MutableMap<Point, Float> = mutableMapOf()

    fun addConnection(point: Point, distance: Float) {
        connections[point] = distance
        point.connections[this] = distance
    }
}
data class Shops(val Name:String, val Floor: Int, val x:Float, val y: Float)

val coordinateSystem = listOf(
    Coordinate(0f,0f),
    Coordinate(1000f,1000f),
)

val points = listOf(
    // corners
    Point(0,169f,365f),
    Point(0,333f,467f),
    Point(0,333f,646f),
    Point(0,632f,467f),
    Point(0,632f,646f),
    Point(0,830f,345f),
    Point(0,798f,744f),

    Point(0,333f,512f),
    Point(0,632f,605f),
    Point(0,394f,467f),
    Point(0,333f,558f),
    Point(0,333f,605f),
    //Point(0,333f,646f),
    Point(0,403f,646f),
    Point(0,482f,646f),
    Point(0,559f,646f),
    //Point(0,632f,646f),
    Point(0,561f,467f),
    Point(0,632f,557f),
    Point(0,632f,512f),
    Point(0,730f,710f),
    Point(0,836f,744f),
    Point(0,679f,439f),
    Point(0,778f,740f),
    Point(0,285f,443f),
    //Point(0,333f,467f),
    Point(0,237f,411f),
    Point(0,207f,396f),
    Point(0,735f,405f),
    Point(0,757f,393f),
    Point(0,845f,336f),
    Point(0,807f,361f),
    Point(0,185f,278f),
    Point(0,165f,361f),
    Point(0,700f,691f),
    Point(0,754f,724f),
    Point(0,855f,744f)
)



val shops = listOf(
    Shops("Polo",0,394f,450f),
    Shops("Starbucks", 0, 836f,772f),
    Shops("Love My Body", 0,482f,665f),
    Shops("The Cookshop", 0,202f,278f),
    Shops("Watson's", 0,303f,605f),
    Shops("Jument", 0,237f,388f),
    Shops("Atasun Optik", 0,303f,645f),
    Shops("W Collection Men", 0,661f,512f),
    Shops("Sevil", 0,315f,435f),
    Shops("Derimod", 0,714f,681f),
    Shops("Hummels", 0,559f,665f),
    Shops("adL", 0,403f,665f),
    Shops("Cafe Vienna", 0,154f,353f),
    Shops("Leman Kültür", 0,845f,306f),
    Shops("W Collection Women", 0,774f,400f),
    Shops("Mavi", 0,303f,512f),
    Shops("Kiğılı",0,661f,605f),
    Shops("Pierre Cardin", 0,303f,558f),
    Shops("Colin's", 0,654f,684f),
    Shops("Efor", 0,561f,450f),
    Shops("Ecrou", 0,661f,557f),
    Shops("Yves Rocher", 0,714f,721f),
    Shops("Vakko", 0,698f,448f),
    Shops("Desa", 0,762f,749f),
    Shops("Kemal Tanca", 0,658f,429f),
    Shops("Hotiç", 0,265f,449f),
    Shops("Saat&Saat", 0,187f,401f),
    Shops("Jepublic", 0,735f,383f),
    Shops("Alaçatı Muhallebicisi", 0,846f,381f),
    Shops("Turkcell", 0,766f,710f),
    Shops("Hayal Kahvesi", 0,855f,716f)
)

val prime = listOf(
    Shops("Polo Prime",0,394f,467f),
    Shops("Starbucks Prime", 0, 836f,744f),
    Shops("Love My Body Prime", 0,482f,646f),
    Shops("The Cookshop Prime", 0,185f,278f),
    Shops("Watson's Prime", 0,333f,605f),
    Shops("Jument Prime", 0,237f,411f),
    Shops("Atasun Optik Prime", 0,333f,646f),
    Shops("W Collection Men Prime", 0,632f,512f),
    Shops("Sevil Prime", 0,333f,467f),
    Shops("Derimod Prime", 0,700f,691f),
    Shops("Hummels Prime", 0,559f,646f),
    Shops("adL Prime", 0,403f,646f),
    Shops("Cafe Vienna Prime", 0,165f,361f),
    Shops("Leman Kültür Prime", 0,845f,336f),
    Shops("W Collection Women Prime", 0,757f,393f),
    Shops("Mavi Prime", 0,333f,512f),
    Shops("Kiğılı Prime",0,632f,605f),
    Shops("Pierre Cardin Prime", 0,333f,558f),
    Shops("Colin's Prime", 0,632f,646f),
    Shops("Efor Prime", 0,561f,467f),
    Shops("Ecrou Prime", 0,632f,557f),
    Shops("Yves Rocher Prime", 0,730f,710f),
    Shops("Vakko Prime", 0,679f,439f),
    Shops("Desa Prime", 0,778f,740f),
    Shops("Kemal Tanca Prime", 0,658f,451f),
    Shops("Hotiç Prime", 0,285f,443f),
    Shops("Saat&Saat Prime", 0,207f,396f),
    Shops("Jepublic Prime", 0,735f,405f),
    Shops("Alaçatı Muhallebicisi Prime", 0,807f,361f),
    Shops("Turkcell Prime", 0,754f,724f),
    Shops("Hayal Kahvesi Prime", 0,855f,744f)
)