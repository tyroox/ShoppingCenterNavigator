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
    Point(0,170f,364f),   //0
    Point(0,333f,467f),   //1
    Point(0,333f,646f),   //2
    Point(0,632f,467f),   //3
    Point(0,632f,646f),   //4
    Point(0,830f,345f),   //5
    Point(0,792f,745f),   //6

    // prime points
    Point(0,403f,646f),     //7 adl
    Point(0,823f,350f),     //8 Alaçatı Muhallebicisi
    Point(0,333f,645f),     //9 Atasun Optik
    Point(0,166f,361f),     //10 Cafe Vienna
    Point(0,666f,667f),     //11 Colin'S
    Point(0,688f,680f),     //12 Derimod
    Point(0,773f,733f),     //13 Desa
    Point(0,632f,557f),     //14 Ecrou
    Point(0,562f,467f),     //15 Efor
    Point(0,855f,744f),     //16 Hayal Kahvesi
    Point(0,278f,433f),     //17 Hotiç
    Point(0,559f,646f),     //18 Hummels
    Point(0,745f,398f),     //19 Jepublic
    Point(0,230f,402f),     //20 Jument
    Point(0,668f,445f),     //21 Kemal Tanca
    Point(0,632f,604f),     //22 Kiğılı
    Point(0,845f,336f),     //23 Leman Kültür
    Point(0,482f,646f),     //24 Love My Body
    Point(0,333f,510f),     //25 Mavi
    Point(0,333f,558f),     //26 Pierre Cardin
    Point(0,394f,467f),     //27 Polo
    Point(0,201f,384f),     //28 Saat&Saat
    Point(0,307f,451f),     //29 Sevil
    Point(0,814f,744f),     //30 Starbucks
    Point(0,186f,278f),     //31 The Cookshop
    Point(0,759f,725f),     //32 Turkcell
    Point(0,679f,439f),     //33 Vakko
    Point(0,632f,512f),     //34 W Collection Men
    Point(0,766f,386f),     //35 W Collection Women
    Point(0,333f,605f),     //36 Watson's
    Point(0,726f,705f),     //37 Yves Rocher

    // stairs
    Point(0,368f,558f),     //38 Merdiven1
    Point(0,482f,622f),     //39 Merdiven2
    Point(0,588f,557f),     //40 Merdiven3
    Point(0,491f,493f),     //41 Merdiven4

    // Corners First Floor
    Point(1,324f,479f),     //42
    Point(1,324f,662f),     //43
    Point(1,628f,662f),     //44
    Point(1,628f,479f),     //45

    // Prime Points First Floor
    Point(1,292f,457f),     //46 Batik
    Point(1,399f,662f),     //47 Creaphone
    Point(1,323f,569f),     //48 D'S Damat
    Point(1,629f,519f),     //49 D&R
    Point(1,689f,698f),     //50 Decathlon
    Point(1,386f,479f),     //51 Dimalis
    Point(1,565f,479f),     //52 ebebek
    Point(1,649f,675f),     //53 Fizyoheal
    Point(1,558f,662f),     //54 Gusto
    Point(1,323f,617f),     //55 igs
    Point(1,267f,441f),     //56 Jakamen
    Point(1,323f,520f),     //57 Journey
    Point(1,324f,662f),     //58 Mudo Collection
    Point(1,260f,436f),     //59 MR DIY
    Point(1,323f,659f),     //60 Perspective
    Point(1,475f,662f),     //61 United Colors of Benetton

    Point(1,365f,569f),     //62 Merdiven 5
    Point(1,475f,638f),     //63 Merdiven 6
    Point(1,592f,567f),     //64 Merdiven 7
    Point(1,484f,499f),     //65 Merdiven 8

)

val prime = listOf(
    Shops("adL Prime", 0,403f,646f),
    Shops("Alaçatı Muhallebicisi Prime", 0,823f,350f),
    Shops("Atasun Optik Prime", 0,333f,645f),
    Shops("Cafe Vienna Prime", 0,166f,361f),
    Shops("Colin's Prime", 0,666f,667f),
    Shops("Derimod Prime", 0,688f,680f),
    Shops("Desa Prime", 0,773f,733f),
    Shops("Ecrou Prime", 0,632f,557f),
    Shops("Efor Prime", 0,562f,467f),
    Shops("Hayal Kahvesi Prime", 0,855f,744f),
    Shops("Hotiç Prime", 0,278f,433f),
    Shops("Hummels Prime", 0,559f,646f),
    Shops("Jepublic Prime", 0,745f,398f),
    Shops("Jument Prime", 0,230f,402f),
    Shops("Kemal Tanca Prime", 0,668f,445f),
    Shops("Kiğılı Prime",0,632f,604f),
    Shops("Leman Kültür Prime", 0,845f,336f),
    Shops("Love My Body Prime", 0,482f,646f),
    Shops("Mavi Prime", 0,333f,510f),
    Shops("Pierre Cardin Prime", 0,333f,558f),
    Shops("Polo Prime",0,394f,467f),
    Shops("Saat&Saat Prime", 0,201f,384f),
    Shops("Sevil Prime", 0,307f,451f),
    Shops("Starbucks Prime", 0, 814f,744f),
    Shops("The Cookshop Prime", 0,186f,278f),
    Shops("Turkcell Prime", 0,759f,725f),
    Shops("Vakko Prime", 0,690f,434f),
    Shops("W Collection Men Prime", 0,632f,512f),
    Shops("W Collection Women Prime", 0,766f,386f),
    Shops("Watson's Prime", 0,333f,605f),
    Shops("Yves Rocher Prime", 0,726f,705f),

    Shops("Batik Prime", 1,292f,457f),
    Shops("Creaphone Prime", 1,399f,662f),
    Shops("D'S Damat Prime", 1,323f,569f),
    Shops("D&R Prime", 1,629f,519f),
    Shops("Decathlon Prime", 1,689f,698f),
    Shops("Dimalis Prime", 1,386f,479f),
    Shops("ebebek Prime", 1,565f,479f),
    Shops("Fizyoheal Prime", 1,649f,675f),
    Shops("Gusto Prime", 1,558f,662f),
    Shops("igs Prime", 1,323f,617f),
    Shops("Jakamen Prime", 1,267f,441f),
    Shops("Journey Prime", 1,323f,520f),
    Shops("Mudo Collection Prime", 1,324f,662f),
    Shops("MR DIY Prime", 1,260f,436f),
    Shops("Perspective Prime", 1,323f,659f),
    Shops("United Colors of Benetton Prime", 1,475f,662f)
)

val shops = listOf(
    Shops("adL", 0,403f,665f),
    Shops("Alaçatı Muhallebicisi", 0,846f,381f),
    Shops("Atasun Optik", 0,301f,645f),
    Shops("Cafe Vienna", 0,154f,353f),
    Shops("Colin's", 0,654f,684f),
    Shops("Derimod", 0,696f,667f),
    Shops("Desa", 0,762f,749f),
    Shops("Ecrou", 0,661f,557f),
    Shops("Efor", 0,562f,448f),
    Shops("Hayal Kahvesi", 0,855f,714f),
    Shops("Hotiç", 0,265f,451f),
    Shops("Hummels", 0,559f,666f),
    Shops("Jepublic", 0,757f,427f),
    Shops("Jument", 0,238f,386f),
    Shops("Kemal Tanca", 0,657f,427f),
    Shops("Kiğılı",0,661f,604f),
    Shops("Leman Kültür", 0,846f,304f),
    Shops("Love My Body", 0,482f,666f),
    Shops("Mavi", 0,301f,510f),
    Shops("Pierre Cardin", 0,301f,558f),
    Shops("Polo",0,394f,448f),
    Shops("Saat&Saat", 0,187f,403f),
    Shops("Sevil", 0,316f,434f),
    Shops("Starbucks", 0, 814f,772f),
    Shops("The Cookshop", 0,205f,278f),
    Shops("Turkcell", 0,770f,709f),
    Shops("Vakko", 0,699f,449f),
    Shops("W Collection Men", 0,661f,512f),
    Shops("W Collection Women", 0,775f,402f),
    Shops("Watson's", 0,301f,605f),
    Shops("Yves Rocher", 0,714f,721f),

    Shops("Batik", 1,312f,429f),
    Shops("Creaphone", 1,399f,681f),
    Shops("D'S Damat", 1,291f,569f),
    Shops("D&R", 1,664f,519f),
    Shops("Decathlon", 1,710f,710f),
    Shops("Dimalis", 1,386f,457f),
    Shops("ebebek", 1,565f,457f),
    Shops("Fizyoheal", 1,633f,701f),
    Shops("Gusto", 1,558f,681f),
    Shops("igs", 1,291f,617f),
    Shops("Jakamen", 1,246f,469f),
    Shops("Journey", 1,291f,520f),
    Shops("Mudo Collection", 1,324f,712f),
    Shops("Mr DIY", 1,246f,426f),
    Shops("Perspective", 1,291f,659f),
    Shops("United Colors of Benetton", 1,475f,681f),
)