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

    // Corners Basement One
    Point(-1,689f,692f),    //66
    Point(-1,689f,519f),    //67
    Point(-1,305f,519f),    //68
    Point(-1,305f,692f),    //69

    // Prime Points Basement One
    Point(-1,373f,518f),    //70 Armağan Oyuncak
    Point(-1,689f,693f),    //71 Barok Diamond
    Point(-1,553f,518f),    //72 Beko
    Point(-1,305f,557f),    //73 Bücürük
    Point(-1,305f,685f),    //74 Dagi
    Point(-1,701f,721f),    //75 Defacto
    Point(-1,689f,536f),    //76 Deichmann
    Point(-1,112f,406f),    //77 Diva
    Point(-1,103f,400f),    //78 Dry Center
    Point(-1,689f,602f),    //79 Focaccia
    Point(-1,689f,603f),    //80 Frederic Patric Parfum
    Point(-1,305f,603f),    //81 Gratis
    Point(-1,283f,505f),    //82 GS Store
    Point(-1,181f,445f),    //83 İstikbal Yatak
    Point(-1,699f,487f),    //84 Joy Park
    Point(-1,548f,693f),    //85 Karaca
    Point(-1,465f,693f),    //86 Linens
    Point(-1,245f,484f),    //87 Özdilek
    Point(-1,689f,649f),    //88 Penti
    Point(-1,190f,452f),    //89 Rock N Roll
    Point(-1,305f,648f),    //90 Siemens
    Point(-1,379f,693f),    //91 Toyzz Shop
    Point(-1,145f,426f),    //92 Vodafone

    Point(-1,464f,530f),    //93 Merdiven 9
    Point(-1,468f,675f),    //94 Merdiven 10
    Point(-1,604f,644f),    //95 Merdiven 11
    Point(-1,604f,652f),    //96 Merdiven 12

    //Corners Second Floor
    Point(2,262f,486f),     //97
    Point(2,660f,486f),     //98
    Point(2,262f,672f),     //99
    Point(2,660f,672f),     //100
    Point(2,337f,486f),     //101
    Point(2,660f,438f),     //102


    // Prime Points Second Floor
    Point(2,337f,412f),     //103 Acıbadem Hastanesi
    Point(2,262f,673f),     //104 Adabella Pizza
    Point(2,262f,584f),     //105 Arby's
    Point(2,295f,486f),     //106 Burger King
    Point(2,546f,486f),     //107 Bursa Kebap Evi
    Point(2,660f,381f),     //108 Chinese & Sushi Express
    Point(2,660f,582f),     //109 Cici Fırın
    Point(2,660f,655f),     //110 Cinema Pink
    Point(2,378f,486f),     //111 Junior Chef
    Point(2,660f,535f),     //112 Just Chik'n
    Point(2,661f,438f),     //113 Köfteci Ramiz
    Point(2,695f,381f),     //114 Mado
    Point(2,410f,672f),     //115 Magic Play
    Point(2,262f,534f),     //116 Salads Mixx
    Point(2,659f,438f),     //117 Tam Sos Döner

    // stairs
    Point(2,354f,586f),     //118 Merdiven 13
    Point(2,578f,581f),     //119 Merdiven 14

    // Prime Points Basement Floor Two
    Point(-2,430f,571f),    //120 Coral Travel
    Point(-2,328f,571f),    //121 Ets Turizm
    Point(-2,497f,571f),    //122 Gymotion
    Point(-2,388f,571f),    //123 Jolly Tur
    Point(-2,320f,571f),    //124 Migros
    Point(-2,497f,519f),    //125 Workinton

    Point(-2,362f,614f),    //125 Merdiven 15

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
    Shops("United Colors of Benetton Prime", 1,475f,662f),

    Shops("Armağan Oyuncak Prime", -1,373f,518f),
    Shops("Barok Diamond Prime", -1,689f,693f),
    Shops("Beko Prime", -1,553f,518f),
    Shops("Bücürük Prime", -1,305f,557f),
    Shops("Dagi Prime", -1,305f,685f),
    Shops("Defacto Prime", -1,701f,721f),
    Shops("Deichmann Prime", -1,689f,536f),
    Shops("Diva Prime", -1,112f,406f),
    Shops("Dry Center Prime", -1,103f,400f),
    Shops("Focaccia Prime", -1,689f,602f),
    Shops("Frederic Patric Parfum Prime", -1,689f,603f),
    Shops("Gratis Prime", -1,305f,603f),
    Shops("GS Store Prime", -1,283f,505f),
    Shops("İstikbal Yatak Prime", -1,181f,445f),
    Shops("Joy Park Prime", -1,699f,487f),
    Shops("Karaca Prime", -1,548f,693f),
    Shops("Linens Prime", -1,465f,693f),
    Shops("Özdilek Prime", -1,245f,484f),
    Shops("Penti Prime", -1,689f,649f),
    Shops("Rock N Roll Prime", -1,190f,452f),
    Shops("Siemens Prime", -1,305f,648f),
    Shops("Toyzz Shop Prime", -1,379f,693f),
    Shops("Vodafone Prime", -1,145f,426f),

    Shops("Acıbadem Hastanesi Prime", 2,337f,412f),
    Shops("Adabella Pizza Prime", 2,262f,673f),
    Shops("Arby's Prime", 2,262f,584f),
    Shops("Burger King Prime", 2,295f,486f),
    Shops("Bursa Kebap Evi Prime", 2,546f,486f),
    Shops("Chinese & Sushi Express Prime", 2,660f,381f),
    Shops("Cici Fırın Prime", 2,660f,582f),
    Shops("Cinema Pink Prime", 2,660f,655f),
    Shops("Junior Chef Prime", 2,378f,486f),
    Shops("Just Chik'n Prime", 2,660f,535f),
    Shops("Köfteci Ramiz Prime", 2,661f,438f),
    Shops("Mado Prime", 2,695f,381f),
    Shops("Magic Play Prime", 2,410f,672f),
    Shops("Salads Mixx Prime", 2,262f,534f),
    Shops("Tam Sos Döner Prime", 2,659f,438f),

    Shops("Coral Travel Prime", -2,430f,571f),
    Shops("Ets Turizm Prime", -2,328f,571f),
    Shops("Gymotion Prime", -2,497f,571f),
    Shops("Jolly Tur Prime", -2,388f,571f),
    Shops("Migros Prime", -2,320f,571f),
    Shops("Workinton Prime", -2,497f,519f),

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

    Shops("Armağan Oyuncak", -1,373f,495f),
    Shops("Barok Diamond", -1,738f,693f),
    Shops("Beko", -1,553f,495f),
    Shops("Bücürük", -1,274f,557f),
    Shops("Dagi", -1,274f,685f),
    Shops("Defacto", -1,705f,733f),
    Shops("Deichmann", -1,738f,536f),
    Shops("Diva", -1,124f,386f),
    Shops("Dry Center", -1,93f,416f),
    Shops("Focaccia", -1,665f,602f),
    Shops("Frederic Patric Parfum", -1,738f,603f),
    Shops("Gratis", -1,274f,603f),
    Shops("GS Store", -1,300f,470f),
    Shops("İstikbal Yatak", -1,192f,424f),
    Shops("Joy Park", -1,704f,471f),
    Shops("Karaca", -1,548f,709f),
    Shops("Linens", -1,465f,709f),
    Shops("Özdilek", -1,231f,509f),
    Shops("Penti", -1,738f,649f),
    Shops("Rock N Roll", -1,182f,467f),
    Shops("Siemens", -1,274f,648f),
    Shops("Toyzz Shop", -1,379f,709f),
    Shops("Vodafone", -1,136f,441f),

    Shops("Acıbadem Hastanesi", 2,382f,382f),
    Shops("Adabella Pizza", 2,194f,673f),
    Shops("Arby's", 2,194f,584f),
    Shops("Burger King", 2,295f,462f),
    Shops("Bursa Kebap Evi", 2,546f,462f),
    Shops("Chinese & Sushi Express", 2,609f,381f),
    Shops("Cici Fırın", 2,738f,582f),
    Shops("Cinema Pink", 2,693f,655f),
    Shops("Junior Chef", 2,378f,462f),
    Shops("Just Chik'n", 2,738f,535f),
    Shops("Köfteci Ramiz", 2,738f,438f),
    Shops("Mado", 2,695f,313f),
    Shops("Magic Play", 2,410f,692f),
    Shops("Salads Mixx", 2,194f,534f),
    Shops("Tam Sos Döner", 2,609f,438f),

    Shops("Coral Travel", -2,430f,543f),
    Shops("Ets Turizm", -2,328f,623f),
    Shops("Gymotion", -2,521f,571f),
    Shops("Jolly Tur", -2,388f,543f),
    Shops("Migros", -2,304f,571f),
    Shops("Workinton", -2,521f,519f),
)