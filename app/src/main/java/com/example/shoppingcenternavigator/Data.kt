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

val malls = listOf("Carousel", "Capacity")

val coordinateSystem = listOf(
    Coordinate(0f,0f),
    Coordinate(1000f,1000f),
)

val carouselPoints = listOf(
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
    Point(0,559f,646f),     //18 Hummel
    Point(0,745f,398f),     //19 Jepublic
    Point(0,230f,402f),     //20 Jument
    Point(0,668f,445f),     //21 Kemal Tanca
    Point(0,632f,604f),     //22 Kiğılı
    Point(0,845f,336f),     //23 Leman Kültür
    Point(0,482f,646f),     //24 Love My Body
    Point(0,333f,510f),     //25 Mavi
    Point(0,333f,558f),     //26 Pierre Cardin
    Point(0,394f,467f),     //27 U.S. Polo Assn
    Point(0,201f,384f),     //28 Saat&Saat
    Point(0,307f,451f),     //29 Sevil
    Point(0,814f,744f),     //30 Starbucks
    Point(0,186f,278f),     //31 The Cookshop
    Point(0,759f,725f),     //32 Turkcell
    Point(0,679f,439f),     //33 Vakko
    Point(0,632f,512f),     //34 W Collection Men
    Point(0,766f,386f),     //35 W Collection Women
    Point(0,333f,605f),     //36 Watsons
    Point(0,726f,705f),     //37 Yves Rocher

    // Stairs
    Point(0,368f,558f),     //38 Merdiven1 (bu kattan aşağıya iniş)
    Point(0,482f,622f),     //39 Merdiven2 (bu kattan yukarıya çıkış)
    Point(0,491f,493f),     //40 Merdiven3 (yukarıdan bu kata gelen)
    Point(0,588f,557f),     //41 Merdiven4 (aşağıdan bu kata çıkan)

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
    Point(1,260f,436f),     //59 Mr. Diy
    Point(1,323f,659f),     //60 Perspective
    Point(1,475f,662f),     //61 United Colors of Benetton

    Point(1,365f,569f),     //62 Merdiven 5 (aşağıdan 1.kata çıkış)
    Point(1,475f,638f),     //63 Merdiven 6 (yukarıdan 1.kata iniş)
    Point(1,592f,567f),     //64 Merdiven 7 (aşağı katlara iniş)
    Point(1,484f,499f),     //65 Merdiven 8 (yukarı katlara çıkış)

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
    Point(-1,181f,445f),    //83 istikbal yatak
    Point(-1,699f,487f),    //84 Joy Park
    Point(-1,548f,693f),    //85 Karaca
    Point(-1,465f,693f),    //86 Linens
    Point(-1,245f,484f),    //87 Özdilek
    Point(-1,689f,649f),    //88 Penti
    Point(-1,190f,452f),    //89 Rock N Roll
    Point(-1,305f,648f),    //90 Siemens
    Point(-1,379f,693f),    //91 Toyzz Shop
    Point(-1,145f,426f),    //92 Vodafone

    Point(-1,464f,530f),    //93 Merdiven A (üst katlara çıkış)
    Point(-1,468f,675f),    //94 Merdiven B (üst kattan iniş)
    Point(-1,604f,648f),    //95 Merdiven C (-2 iniş ve çıkış)
    Point(-1,464f,518f),    //96 Merdiven Bağlama Point

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
    Point(2,354f,584f),     //118 Merdiven 13 (kattan aşağı iniş)
    Point(2,578f,582f),      //119 Merdiven 14 (aşağıdan yukarı çıkış)

    // Prime Points Basement Floor Two
    Point(-2,430f,571f),    //120 Coral Travel
    Point(-2,328f,571f),    //121 ETS Turizm
    Point(-2,497f,571f),    //122 Gymotion
    Point(-2,388f,571f),    //123 Jolly Tur
    Point(-2,320f,571f),    //124 Migros
    Point(-2,497f,519f),    //125 Workinton

    Point(-2,362f,614f),    //126 Merdiven 15
)

val carouselPrime = listOf(
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
    Shops("Hummel Prime", 0,559f,646f),
    Shops("Jepublic Prime", 0,745f,398f),
    Shops("Jument Prime", 0,230f,402f),
    Shops("Kemal Tanca Prime", 0,668f,445f),
    Shops("Kiğılı Prime",0,632f,604f),
    Shops("Leman Kültür Prime", 0,845f,336f),
    Shops("Love My Body Prime", 0,482f,646f),
    Shops("Mavi Prime", 0,333f,510f),
    Shops("Pierre Cardin Prime", 0,333f,558f),
    Shops("U.S. Polo Assn. Prime",0,394f,467f),
    Shops("Saat&Saat Prime", 0,201f,384f),
    Shops("Sevil Prime", 0,307f,451f),
    Shops("Starbucks Prime", 0, 814f,744f),
    Shops("The Cookshop Prime", 0,186f,278f),
    Shops("Turkcell Prime", 0,759f,725f),
    Shops("Vakko Prime", 0,690f,434f),
    Shops("W Collection Men Prime", 0,632f,512f),
    Shops("W Collection Women Prime", 0,766f,386f),
    Shops("Watsons Prime", 0,333f,605f),
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
    Shops("Mr. Diy Prime", 1,260f,436f),
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
    Shops("istikbal yatak Prime", -1,181f,445f),
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
    Shops("ETS Turizm Prime", -2,328f,571f),
    Shops("Gymotion Prime", -2,497f,571f),
    Shops("Jolly Tur Prime", -2,388f,571f),
    Shops("Migros Prime", -2,320f,571f),
    Shops("Workinton Prime", -2,497f,519f),

    )

val carouselShops = listOf(
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
    Shops("Hummel", 0,559f,666f),
    Shops("Jepublic", 0,757f,427f),
    Shops("Jument", 0,238f,386f),
    Shops("Kemal Tanca", 0,657f,427f),
    Shops("Kiğılı",0,661f,604f),
    Shops("Leman Kültür", 0,846f,304f),
    Shops("Love My Body", 0,482f,666f),
    Shops("Mavi", 0,301f,510f),
    Shops("Pierre Cardin", 0,301f,558f),
    Shops("U.S. Polo Assn.",0,394f,448f),
    Shops("Saat&Saat", 0,187f,403f),
    Shops("Sevil", 0,316f,434f),
    Shops("Starbucks", 0, 814f,772f),
    Shops("The Cookshop", 0,205f,278f),
    Shops("Turkcell", 0,770f,709f),
    Shops("Vakko", 0,699f,449f),
    Shops("W Collection Men", 0,661f,512f),
    Shops("W Collection Women", 0,775f,402f),
    Shops("Watsons", 0,301f,605f),
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
    Shops("Mr. Diy", 1,246f,426f),
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
    Shops("istikbal yatak", -1,192f,424f),
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
    Shops("ETS Turizm", -2,328f,623f),
    Shops("Gymotion", -2,521f,571f),
    Shops("Jolly Tur", -2,388f,543f),
    Shops("Migros", -2,304f,571f),
    Shops("Workinton", -2,521f,519f),
)

val capacityPoints = listOf(

    // First Floor Corners
    Point(1,177f,544f),    // 0
    Point(1,265f,540f),    // 1
    Point(1,276f,461f),    // 2
    Point(1,163f,461f),    // 3
    Point(1,276f,362f),    // 4
    Point(1,163f,362f),    // 5
    Point(1,276f,339f),    // 6
    Point(1,450f,236f),    // 7
    Point(1,163f,255f),    // 8
    Point(1,312f,168f),    // 9
    Point(1,504f,168f),    // 10
    Point(1,504f,236f),    // 11
    Point(1,628f,168f),    // 12
    Point(1,628f,236f),    // 13
    Point(1,689f,256f),    // 14
    Point(1,689f,448f),    // 15
    Point(1,734f,542f),    // 16
    Point(1,630f,538f),    // 17

    // First Floor Shops
    Point(1,768f,189f),   // 18 Mango-101
    Point(1,754f,229f),   // 19 Lufian-102
    Point(1,689f,275f),   // 20 Oxxo-103
    Point(1,689f,305f),   // 21 Loris-104
    Point(1,689f,359f),   // 22 LTB-105
    Point(1,747f,477f),   // 23 Mavi-106
    Point(1,767f,507f),   // 24 Sneaks Up-107
    Point(1,670f,549f),   // 25 Gratis-109
    Point(1,554f,509f),   // 26 Jimmy Key-110
    Point(1,482f,509f),   // 27 U.S Polo Assn.-111
    Point(1,399f,509f),   // 28 Oysho-112
    Point(1,220f,549f),   // 29 Zara-113
    Point(1,143f,520f),   // 30 L'Occitane-114
    Point(1,143f,501f),   // 31 Yves Rocher-115
    Point(1,163f,475f),   // 32 Kiko-116
    Point(1,163f,432f),   // 33 Konyalı Saat-117
    Point(1,163f,393f),   // 34 Timberland-118
    Point(1,163f,354f),   // 35 Calzedonia-119
    Point(1,163f,312f),   // 36 Elle-120
    Point(1,163f,272f),   // 37 Greyder-121
    Point(1,194f,237f),   // 38 Pull&Bear-122
    Point(1,276f,189f),   // 39 Bershka-123
    Point(1,348f,168f),   // 40 Mi Store-124
    Point(1,417f,168f),   // 41 GözGrup Optik-125
    Point(1,493f,168f),   // 42 Birkenstock-126
    Point(1,563f,168f),   // 43 Samsonite-127
    Point(1,624f,168f),   // 44 Flormar-128
    Point(1,686f,156f),   // 45 Pandora-129
    Point(1,736f,164f),   // 46 Swarovski-130
    Point(1,615f,236f),   // 47 Sunglass Hut / Ray-Ban-131
    Point(1,689f,337f),   // 48 Stradivarius-132
    Point(1,689f,413f),   // 49 Lee Wrangler-133
    Point(1,632f,483f),   // 50 Stylish-134
    Point(1,485f,509f),   // 51 Skechers-135
    Point(1,413f,509f),   // 52 intimissimi-136
    Point(1,276f,433f),   // 53 Camper-138
    Point(1,276f,381f),   // 54 Sephora-139
    Point(1,365f,286f),   // 55 Starbucks-140
    Point(1,490f,236f),   // 56 Dagi-141
    Point(1,555f,236f),   // 57 GMG Firenze-142
    Point(1,223f,461f),   // 58 Vodafone-Cep Point-143
    Point(1,220f,362f),   // 59 Golden Rose-144
    Point(1,222f,362f),   // 60 Boble Bubble Tea-145
    Point(1,504f,198f),   // 61 Avon-146
    Point(1,504f,199f),   // 62 Lelas-147
    Point(1,628f,199f),   // 63 Cella-148

    // First Floor Stairs
    Point(1,143f,509f),   // 64 Stair A Down
    Point(1,288f,509f),   // 65 Stair A Up
    Point(1,615f,509f),   // 66 Stair B Down
    Point(1,628f,201f),   // 67 Stair C Down
    Point(1,764f,201f),   // 68 Stair C Up

    // Corners Second Floor
    Point(2,612f,219f),      // 69
    Point(2,612f,258f),      // 70
    Point(2,300f,215f),      // 71
    Point(2,430f,261f),      // 72
    Point(2,162f,293f),      // 73

    Point(2,250f,365f),      // 74
    Point(2,162f,511f),      // 75
    Point(2,250f,511f),      // 76

    Point(2,149f,527f),      // 77
    Point(2,265f,527f),      // 78

    Point(2,705f,273f),      // 79
    Point(2,158f,575f),      // 80

    Point(2,478f,261f),      // 81
    Point(2,430f,215f),      // 82
    Point(2,163f,372f),      // 83

    Point(2,264f,569f),      // 84
    Point(2,194f,588f),      // 85
    Point(2,245f,588f),      // 86
    Point(2,220f,588f),      // 87

    Point(2,724f,216f),      // 88
    Point(2,635f,206f),      // 89
    Point(2,534f,214f),      // 90
    Point(2,534f,261f),      // 91


    // Prime Points Second Floor
    Point(2,733f,240f),      // 92 - 201/Özsüt
    Point(2,733f,269f),      // 93 - 202/Gloria Jeans Coffees
    Point(2,655f,274f),      // 94 - 203/D&R
    Point(2,352f,306f),      // 95 - 204/Pidem
    Point(2,250f,386f),      // 96 - 205/Bursa Kebap Evi
    Point(2,250f,425f),      // 97 - 206/Günaydın
    Point(2,250f,469f),      // 98 - 207/Paribu Cineverse
    Point(2,250f,504f),      // 99 - 208/HD iskender
    Point(2,276f,546f),      // 100 - 209/Oyun Zamanı
    Point(2,255f,588f),      // 101 - 210/Fitcity
    Point(2,260f,664f),      // 102 - 212/Mediamarkt
    Point(2,220f,664f),      // 103 - 213/Molasera
    Point(2,220f,612f),      // 104 - 214/Tavuk Dünyası
    Point(2,142f,546f),      // 105 - 215/Green Salads
    Point(2,162f,454f),      // 106 - 216 - 217/GurmeBurger Kasap
    Point(2,162f,396f),      // 107 - 218/Bavra Pide
    Point(2,162f,355f),      // 108 - 219/Köfteci Ramiz
    Point(2,162f,318f),      // 109 - 220/Makarnam
    Point(2,178f,284f),      // 110 - 221/Dürümle
    Point(2,218f,260f),      // 111 - 222/Burger King
    Point(2,279f,226f),      // 112 - 223/Mado
    Point(2,338f,215f),      // 113 - 224/Usta Dönerci
    Point(2,408f,215f),      // 114 - 225/Popeyes
    Point(2,478f,215f),      // 115 - 226/Arby's
    Point(2,680f,200f),      // 116 - 228/KFC
    Point(2,534f,237f),      // 117 - 229/Direct Protect

    // Stairs
    Point(2,142f,545f),      // 118 stair 1
    Point(2,604f,238f),      // 119 stair 2

    // Ground Floor Corners
    Point(0,712f,173f),   // 120
    Point(0,677f,263f),   // 121
    Point(0,599f,240f),   // 122
    Point(0,499f,240f),   // 123
    Point(0,449f,240f),   // 124
    Point(0,266f,347f),   // 125
    Point(0,142f,366f),   // 126
    Point(0,267f,184f),   // 127
    Point(0,142f,474f),   // 128
    Point(0,266f,474f),   // 129
    Point(0,174f,576f),   // 130
    Point(0,26f,661f),    // 131
    Point(0,677f,467f),   // 132

    // Ground Floor Shops
    Point(0,746f,193f),   // 133 Midpoint Z-01
    Point(0,746f,239f),   // 134 Marks&Spencer Z-02
    Point(0,677f,297f),   // 135 Saat&Saat Z-03
    Point(0,677f,367f),   // 136 Divarese Z-04
    Point(0,677f,407f),   // 137 Suwen Z-05
    Point(0,677f,446f),   // 138 Beymen Club Z-06
    Point(0,747f,496f),   // 139 English Home Z-07
    Point(0,755f,525f),   // 140 Tüzün Z-08
    Point(0,727f,551f),   // 141 Kosmika Z-09
    Point(0,642f,558f),   // 142 Faik Sönmez Z-10
    Point(0,498f,521f),   // 143 Network Men Z-11
    Point(0,387f,521f),   // 144 Tommy Hilfiger Z-12
    Point(0,231f,559f),   // 145 Blue Diamond Z-13
    Point(0,231f,658f),   // 146 Pierre Cardin Z-14
    Point(0,231f,719f),   // 147 W Collection Z-15
    Point(0,231f,781f),   // 148 Madame Coco Z-16
    Point(0,231f,804f),   // 149 Paşabahçe Mağazaları Z-17
    Point(0,231f,791f),   // 150 Pelit Z-18
    Point(0,231f,685f),   // 151 Massimo Dutti Z-19
    Point(0,26f,712f),   // 152 Tobacco Shop Z-20
    Point(0,128f,603f),   // 153 Cacharel Z-21
    Point(0,121f,516f),   // 154 Derimod Z-22
    Point(0,142f,443f),   // 155 Avva Z-23
    Point(0,142f,403f),   // 156 Hotiç Z-24
    Point(0,142f,326f),   // 157 Network Women Z-25
    Point(0,142f,256f),   // 158 Lizay Z-26
    Point(0,150f,251f),   // 159 Damat Tween Z-27
    Point(0,255f,191f),   // 160 Hugo Boss Z-28
    Point(0,359f,184f),   // 161 Cookshop Z-29
    Point(0,499f,184f),   // 162 Chocolate Z-30
    Point(0,599f,184f),   // 163 Mac Z-31
    Point(0,663f,170f),   // 164 Capacity Eczane Z-32
    Point(0,632f,253f),   // 165 Atasay Z-33
    Point(0,677f,323f),   // 166 Superstep Z-34
    Point(0,677f,406f),   // 167 Utopian Z-36
    Point(0,623f,485f),   // 168 ipekyol Z-37
    Point(0,480f,521f),   // 169 Twist Z-38
    Point(0,403f,521f),   // 170 Mocassini Z-39
    Point(0,330f,521f),   // 171 So Chic Z-40
    Point(0,266f,449f),   // 172 Tanca Plus Z-41
    Point(0,266f,406f),   // 173 Ramsey Z-42
    Point(0,266f,366f),   // 174 Vakko Z-43
    Point(0,322f,315f),   // 175 Zen Z-44
    Point(0,394f,272f),   // 176 LuaPie Z-45
    Point(0,515f,240f),   // 177 Lacoste Z-46

    // Ground Floor Stairs
    Point(0,746f,214f),   // 178 Stair C Up
    Point(0,599f,214f),   // 179 Stair C Down
    Point(0,121f,523f),   // 180 Stair A Down
    Point(0,266f,521f),   // 181 Stair A Up
    Point(0,603f,521f),   // 182 Stair B Down
    Point(0,754f,521f),   // 183 Stair B Up

    // Corners Basement -1 Floor
    Point(-1,763f,189f),      // 184
    Point(-1,646f,191f),      // 185
    Point(-1,646f,248f),      // 186
    Point(-1,343f,191f),      // 187
    Point(-1,482f,248f),      // 188
    Point(-1,570f,191f),      // 189
    Point(-1,570f,248f),      // 190
    Point(-1,188f,280f),      // 191
    Point(-1,315f,344f),      // 192
    Point(-1,188f,371f),      // 193
    Point(-1,315f,371f),      // 194
    Point(-1,188f,433f),      // 195
    Point(-1,315f,433f),      // 196
    Point(-1,188f,463f),      // 197
    Point(-1,315f,463f),      // 198
    Point(-1,188f,642f),      // 199
    Point(-1,315f,642f),      // 200
    Point(-1,658f,490f),      // 201
    Point(-1,749f,490f),      // 202
    Point(-1,652f,558f),      // 203
    Point(-1,705f,268f),      // 204
    Point(-1,705f,481f),      // 205

    // Prime Points Basement -1
    Point(-1,788f,212f),      // 206 - B-01/Koton
    Point(-1,764f,257f),      // 207 - B-02/Atelier Rebul
    Point(-1,705f,299f),      // 208 - B-03/Jumbo Prime
    Point(-1,705f,385f),      // 209 - B-05/Yatsan
    Point(-1,705f,416f),      // 210 - B-06/Watsons
    Point(-1,782f,513f),      // 211 - B-07/Nike
    Point(-1,774f,549f),      // 212 - B-08/Phone Hospital
    Point(-1,745f,564f),      // 213 - B-09/Vodafone
    Point(-1,704f,569f),      // 214 - B-10/Turkcell-Genpa
    Point(-1,580f,525f),      // 215 - B-11/Ender Spor
    Point(-1,509f,525f),      // 216 - B-12/Eve
    Point(-1,442f,525f),      // 217- B-13/Atasun Optik
    Point(-1,315f,557f),      // 218 - B-14/Safranbolu Lokumcusu
    Point(-1,315f,598f),      // 219 - B-15/ETS Turizm
    Point(-1,315f,640f),      // 220 - B-16/Jolly Tur
    Point(-1,249f,642f),      // 221 - B-17/Migros MMM
    Point(-1,188f,640f),      // 222 - B-18/Şenol Zeytinoğlu - Kadın
    Point(-1,188f,602f),      // 223 - B-19/Linens
    Point(-1,188f,555f),      // 224 - B-20/Panista
    Point(-1,188f,491f),      // 225 - B-21/B&G Store
    Point(-1,188f,409f),      // 226 - B-22/Penti
    Point(-1,188f,332f),      // 227 - B-24/Kifidis
    Point(-1,188f,298f),      // 228 - B-25/Mamino
    Point(-1,205f,270f),      // 229 - B-26/Toyzz Shop

    Point(-1,263f,237f),      // 230 - B-27/Armağan Oyuncak
    Point(-1,324f,202f),      // 231 - B-28/Flo
    Point(-1,393f,191f),      // 232 - B-29/Ceyo
    Point(-1,453f,191f),      // 233 - B-30/Korkmaz
    Point(-1,525f,191f),      // 234 - B-31/Arçelik
    Point(-1,593f,191f),      // 235 - B-32/Samsung
    Point(-1,673f,186f),      // 236 - B-33/Tefal
    Point(-1,722f,178f),      // 237 - B-34/Home Sweet Home
    Point(-1,407f,291f),      // 238 - B-35/LC Waikiki
    Point(-1,705f,373f),      // 239 - B-36/Columbia
    Point(-1,705f,409f),      // 240 - B-37/The North Face
    Point(-1,705f,452f),      // 241 - B-38/Adidas
    Point(-1,512f,525f),      // 242 - B-39/Kartal Yuvası
    Point(-1,315f,497f),      // 243 - B-40/Panço
    Point(-1,315f,447f),      // 244 - B-41/Tchibo
    Point(-1,315f,394f),      // 245 - B-42/Ayakkabı Dünyası

    //Red Points Primes
    Point(-1,283f,642f),      // 246 - B-43/Çiğköftem
    Point(-1,215f,642f),      // 247 - B-44/Haribo Çok Şeker
    Point(-1,188f,523f),      // 248 - B-45/Aydın Döviz & Altın
    Point(-1,250f,463f),      // 249 - B-46/Mad Parfumeur
    Point(-1,251f,433f),      // 250 - B-47/Malatya Pazarı Palancı
    Point(-1,250f,433f),      // 251 - B-48/Arifoğlu
    Point(-1,251f,371f),      // 252 - B-49/Krispy Kreme
    Point(-1,250f,371f),      // 253 - B-50/Cafe Poi
    Point(-1,570f,219f),      // 254 - B-51/Sihirli Eller
    Point(-1,710f,178f),      // 255 - B-52/Rainwater
    Point(-1,705f,280f),      // 256 - B-53/Akıl ve Zeka Oyunları
    Point(-1,615f,525f),      // 257 - B-54/Cezve Kahve

    // Stairs
    Point(-1,788f,223f),      // 258 stairs 1
    Point(-1,782f,525f),      // 259 stairs 2
    Point(-1,315f,525f),      // 260 stairs 3
)

val capacityPrime = listOf (

    //First Floor Primes
    Shops("Mango Prime", 1,768f,189f),    // 101
    Shops("Lufian Prime", 1,754f,229f),   // 102
    Shops("Oxxo Prime", 1,689f,275f),     // 103
    Shops("Loris Prime", 1,689f,305f),    // 104
    Shops("LTB Prime", 1,689f,359f),      // 105
    Shops("Mavi Prime", 1,747f,477f),     // 106
    Shops("Sneaks Up Prime", 1,767f,507f),    // 107
    Shops("Gratis Prime", 1,670f,549f),   //109
    Shops("Jimmy Key Prime", 1,554f,509f),   //110
    Shops("U.S. Polo Assn.", 1,482f,509f),   //111
    Shops("Oysho Prime", 1,399f,509f),   //112
    Shops("Zara Prime", 1,220f,549f),   //113
    Shops("L'Occitane Prime", 1,143f,520f),   //114
    Shops("Yves Rocher Prime", 1,143f,501f),   //115
    Shops("Kiko Prime", 1,163f,475f),   //116
    Shops("Konyalı Saat Prime", 1,163f,432f),   //117
    Shops("Timberland Prime", 1,163f,393f),   //118
    Shops("Calzedonia Prime", 1,163f,354f),   //119
    Shops("Elle Prime", 1,163f,312f),   //120
    Shops("Greyder Prime", 1,163f,272f),   //121
    Shops("Pull&Bear Prime", 1,194f,237f),   //122
    Shops("Bershka Prime", 1,276f,189f),   //123
    Shops("Mi Store Prime", 1,348f,168f),   //124
    Shops("GözGrup Optik Prime", 1,417f,168f),   //125
    Shops("Birkenstock Prime", 1,493f,168f),   //126
    Shops("Samsonite Prime", 1,563f,168f),   //127
    Shops("Flormar Prime", 1,624f,168f),   //128
    Shops("Pandora Prime", 1,686f,156f),   //129
    Shops("Swarovski Prime", 1,736f,164f),   //130
    Shops("Sunglass Hut / Ray-Ban Prime", 1,615f,236f),   //131
    Shops("Stradivarius Prime", 1,689f,337f),   //132
    Shops("Lee Wrangler Prime", 1,689f,413f),   //133
    Shops("Stylish Prime", 1,632f,483f),   //134
    Shops("Skechers Prime", 1,485f,509f),   //135
    Shops("intimissimi Prime", 1,413f,509f),   //136
    Shops("Camper Prime", 1,276f,433f),   //138
    Shops("Sephora Prime", 1,276f,381f),   //139
    Shops("Starbucks Prime", 1,365f,286f),   //140
    Shops("Dagi Prime", 1,490f,236f),   //141
    Shops("GMG Firenze Prime", 1,555f,236f),   //142
    Shops("Vodafone-Cep Point Prime", 1,223f,461f),   //143
    Shops("Golden Rose Prime", 1,220f,362f),   //144
    Shops("Boble Bubble Tea Prime", 1,222f,362f),   //145
    Shops("Avon Prime", 1,504f,198f),   //146
    Shops("Lelas Prime", 1,504f,199f),   //147
    Shops("Cella Prime", 1,628f,199f),   //148

    // Second Floor
    Shops("Özsüt Prime", 2,733f,240f),                            // 201
    Shops("Gloria Jeans Coffees Prime", 2,733f,269f),             // 202
    Shops("D&R Prime", 2,655f,274f),                              // 203
    Shops("Pidem Prime", 2,352f,306f),                            // 204
    Shops("Bursa Kebap Evi Prime", 2,250f,386f),                  // 205
    Shops("Günaydın Prime", 2,250f,425f),                         // 206
    Shops("Paribu Cineverse Prime", 2,250f,469f),                 // 207
    Shops("HD iskender Prime", 2,250f,504f),                      // 208
    Shops("Oyun Zamanı Prime", 2,276f,546f),                      // 209

    Shops("Fitcity Prime", 2,255f,588f),                          // 210
    Shops("Mediamarkt Prime", 2,260f,664f),                       // 212
    Shops("Molasera Prime", 2,220f,664f),                         // 213
    Shops("Tavuk Dünyası Prime", 2,220f,612f),                    // 214

    Shops("Green Salads Prime", 2,142f,546f),                     // 215
    Shops("GurmeBurger Kasap Prime", 2,162f,454f),                // 216 - 217
    Shops("Bavra Pide Prime", 2,162f,396f),                       // 218
    Shops("Köfteci Ramiz Prime", 2,162f,355f),                    // 219
    Shops("Makarnam Prime", 2,162f,318f),                          // 220
    Shops("Dürümle Prime", 2,178f,284f),                          // 221
    Shops("Burger King Prime", 2,218f,260f),                      // 222
    Shops("Mado Prime", 2,279f,226f),                             // 223
    Shops("Usta Dönerci Prime", 2,338f,215f),                     // 224
    Shops("Popeyes Prime", 2,408f,215f),                          // 225
    Shops("Arby's Prime", 2,478f,215f),                           // 226
    Shops("KFC Prime", 2,680f,200f),                              // 228
    // Red Points
    Shops("Direct Protect Prime", 2,534f,237f),                   // 229

    // Ground Floor Primes
    Shops("Midpoint Prime", 0,746f,193f),   //Z-01
    Shops("Marks&Spencer Prime", 0,746f,239f),   //Z-02
    Shops("Saat&Saat Prime", 0,677f,297f),   //Z-03
    Shops("Divarese Prime", 0,677f,367f),   //Z-04
    Shops("Suwen Prime", 0,677f,407f),   //Z-05
    Shops("Beymen Club Prime", 0,677f,446f),   //Z-06
    Shops("English Home Prime", 0,747f,496f),   //Z-07
    Shops("Tüzün Prime", 0,755f,525f),   //Z-08
    Shops("Kosmika Prime", 0,727f,551f),   //Z-09
    Shops("Faik Sönmez Prime", 0,642f,558f),   //Z-10
    Shops("Network Men Prime", 0,498f,521f),   //Z-11
    Shops("Tommy Hilfiger Prime", 0,387f,521f),   //Z-12
    Shops("Blue Diamond Prime", 0,231f,559f),   //Z-13
    Shops("Pierre Cardin Prime", 0,231f,658f),   //Z-14
    Shops("W Collection Prime", 0,231f,719f),   //Z-15
    Shops("Madame Coco Prime", 0,231f,781f),   //Z-16
    Shops("Paşabahçe Mağazaları Prime", 0,231f,804f),   //Z-17
    Shops("Pelit Prime", 0,231f,791f),   //Z-18
    Shops("Massimo Dutti Prime", 0,231f,685f),   //Z-19
    Shops("Tobacco Shop Prime", 0,26f,712f),   //Z-20
    Shops("Cacharel Prime", 0,128f,603f),   //Z-21
    Shops("Derimod Prime", 0,121f,516f),   //Z-22
    Shops("Avva", 0,142f,443f),   //Z-23
    Shops("Hotiç", 0,142f,403f),   //Z-24
    Shops("Network Women", 0,142f,326f),   //Z-25
    Shops("Lizay", 0,142f,256f),   //Z-26
    Shops("Damat Tween", 0,150f,251f),   //Z-27
    Shops("Hugo Boss", 0,255f,191f),   //Z-28
    Shops("Cookshop", 0,359f,184f),   //Z-29
    Shops("Chocolate", 0,499f,184f),   //Z-30
    Shops("Mac", 0,599f,184f),   //Z-31
    Shops("Capacity Eczane", 0,663f,170f),   //Z-32
    Shops("Atasay", 0,632f,253f),   //Z-33
    Shops("SuperStep", 0,677f,323f),   //Z-34
    Shops("Utopian", 0,677f,406f),   //Z-36
    Shops("ipekyol", 0,623f,485f),   //Z-37
    Shops("Twist", 0,480f,521f),   //Z-38
    Shops("Mocassini", 0,403f,521f),   //Z-39
    Shops("So Chic", 0,330f,521f),   //Z-40
    Shops("Tanca Plus", 0,266f,449f),   //Z-41
    Shops("Ramsey", 0,266f,406f),   //Z-42
    Shops("Vakko", 0,266f,366f),   //Z-43
    Shops("Zen", 0,322f,315f),   //Z-44
    Shops("LuaPie", 0,394f,272f),   //Z-45
    Shops("Lacoste", 0,515f,240f),   //Z-46

    Shops("Koton Prime", -1,788f,212f),                           // B-01
    Shops("Atelier Rebul Prime", -1,764f,257f),                   // B-02
    Shops("Jumbo Prime", -1,705f,299f),                           // B-03
    Shops("Yatsan Prime", -1,705f,385f),                          // B-05
    Shops("Watsons Prime", -1,705f,416f),                         // B-06
    Shops("Nike Prime", -1,782f,513f),                            // B-07
    Shops("Phone Hospital Prime", -1,774f,549f),                  // B-08
    Shops("Vodafone Prime", -1,745f,564f),                        // B-09
    Shops("Turkcell-Genpa Prime", -1,704f,569f),                  // B-10
    Shops("Ender Spor Prime", -1,580f,525f),                      // B-11
    Shops("Eve Prime", -1,509f,525f),                             // B-12
    Shops("Atasun Optik Prime", -1,442f,525f),                    // B-13
    Shops("Safranbolu Lokumcusu Prime", -1,315f,557f),            // B-14
    Shops("ETS Turizm Prime", -1,315f,598f),                         // B-15
    Shops("Jolly Tur Prime", -1,315f,640f),                       // B-16
    Shops("Migros MMM Prime", -1,249f,642f),                      // B-17
    Shops("Şenol Zeytinoğlu - Kadın Prime", -1,188f,640f),        // B-18
    Shops("Linens Prime", -1,188f,602f),                          // B-19
    Shops("Panista Prime", -1,188f,555f),                         // B-20
    Shops("B&G Store Prime", -1,188f,491f),                       // B-21
    Shops("Penti Prime", -1,188f,409f),                           // B-22
    Shops("Kifidis Prime", -1,188f,332f),                         // B-24
    Shops("Mamino Prime", -1,188f,298f),                          // B-25
    Shops("Toyzz Shop Prime", -1,205f,270f),                      // B-26
    Shops("Armağan Oyuncak Prime", -1,263f,237f),                 // B-27
    Shops("Flo Prime", -1,324f,202f),                             // B-28
    Shops("Ceyo Prime", -1,393f,191f),                            // B-29
    Shops("Korkmaz Prime", -1,453f,191f),                         // B-30
    Shops("Arçelik Prime", -1,525f,191f),                         // B-31
    Shops("Samsung Prime", -1,593f,191f),                         // B-32
    Shops("Tefal Prime", -1,673f,186f),                           // B-33
    Shops("Home Sweet Home Prime", -1,722f,178f),                 // B-34
    Shops("LC Waikiki Prime", -1,407f,291f),                      // B-35
    Shops("Columbia Prime", -1,705f,373f),                        // B-36
    Shops("The North Face Prime", -1,705f,409f),                  // B-37
    Shops("Adidas Prime", -1,705f,452f),                          // B-38
    Shops("Kartal Yuvası Prime", -1,512f,525f),                   // B-39
    Shops("Panço Prime", -1,315f,497f),                           // B-40
    Shops("Tchibo Prime", -1,315f,447f),                          // B-41
    Shops("Ayakkabı Dünyası Prime", -1,315f,394f),                // B-42

    //Red points
    Shops("Çiğköftem Prime", -1,283f,642f),                       // B-43
    Shops("Haribo Çok Şeker Prime", -1,215f,642f),                // B-44
    Shops("Aydın Döviz & Altın Prime", -1,188f,523f),             // B-45
    Shops("Mad Parfumeur Prime", -1,250f,463f),                   // B-46
    Shops("Malatya Pazarı Palancı Prime", -1,251f,433f),          // B-47
    Shops("Arifoğlu Prime", -1,250f,433f),                        // B-48
    Shops("Krispy Kreme Prime", -1,251f,371f),                    // B-49
    Shops("Cafe Poi Prime", -1,250f,371f),                        // B-50
    Shops("Sihirli Eller Prime", -1,570f,219f),                   // B-51
    Shops("Rainwater Prime", -1,710f,178f),                       // B-52
    Shops("Akıl ve Zeka Oyunları Prime", -1,705f,280f),           // B-53
    Shops("Cezve Kahve Prime", -1,615f,525f),                     // B-54
)

val capacityShops = listOf(
    // First Floor Shops
    Shops("Mango", 1,789f,184f),    // 101
    Shops("Lufian", 1,769f,239f),   // 102
    Shops("Oxxo", 1,724f,275f),     // 103
    Shops("Loris", 1,724f,305f),    // 104
    Shops("LTB", 1,721f,359f),      // 105
    Shops("Mavi", 1,757f,464f),     // 106
    Shops("Sneaks Up", 1,787f,507f),    // 107
    Shops("Gratis", 1,665f,561f),   //109
    Shops("Jimmy Key", 1,554f,524f),   //110
    Shops("U.S. Polo Assn.", 1,482f,524f),   //111
    Shops("Oysho", 1,399f,524f),   //112
    Shops("Zara", 1,220f,560f),   //113
    Shops("L'Occitane", 1,124f,523f),   //114
    Shops("Yves Rocher", 1,124f,500f),   //115
    Shops("Kiko", 1,140f,475f),   //116
    Shops("Konyalı Saat", 1,141f,432f),   //117
    Shops("Timberland", 1,141f,393f),   //118
    Shops("Calzedonia", 1,141f,354f),   //119
    Shops("Elle", 1,141f,312f),   //120
    Shops("Greyder", 1,141f,272f),   //121
    Shops("Pull&Bear", 1,180f,223f),   //122
    Shops("Bershka", 1,263f,176f),   //123
    Shops("Mi Store", 1,348f,154f),   //124
    Shops("GözGrup Optik", 1,417f,154f),   //125
    Shops("Birkenstock", 1,493f,154f),   //126
    Shops("Samsonite", 1,563f,154f),   //127
    Shops("Flormar", 1,624f,154f),   //128
    Shops("Pandora", 1,686f,143f),   //129
    Shops("Swarovski", 1,741f,151f),   //130
    Shops("Sunglass Hut / Ray-Ban", 1,615f,252f),   //131
    Shops("Stradivarius", 1,661f,337f),   //132
    Shops("Lee Wrangler", 1,661f,413f),   //133
    Shops("Stylish", 1,616f,467f),   //134
    Shops("Skechers", 1,485f,488f),   //135
    Shops("intimissimi", 1,413f,488f),   //136
    Shops("Camper", 1,299f,433f),   //138
    Shops("Sephora", 1,299f,381f),   //139
    Shops("Starbucks", 1,378f,299f),   //140
    Shops("Dagi", 1,490f,252f),   //141
    Shops("GMG Firenze", 1,555f,252f),   //142
    Shops("Vodafone-Cep Point", 1,223f,439f),   //143
    Shops("Golden Rose", 1,220f,388f),   //144
    Shops("Boble Bubble Tea", 1,222f,340f),   //145
    Shops("Avon", 1,462f,198f),   //146
    Shops("Lelas", 1,529f,199f),   //147
    Shops("Cella", 1,590f,199f),   //148

    // Second Floor
    Shops("Özsüt", 2,770f,240f),                            // 201
    Shops("Gloria Jeans Coffees", 2,770f,269f),             // 202
    Shops("D&R", 2,655f,280f),                              // 203
    Shops("Pidem", 2,358f,314f),                            // 204
    Shops("Bursa Kebap Evi", 2,263f,386f),                  // 205
    Shops("Günaydın", 2,263f,425f),                         // 206
    Shops("Paribu Cineverse", 2,373f,469f),                 // 207
    Shops("HD iskender", 2,263f,504f),                      // 208
    Shops("Oyun Zamanı", 2,288f,546f),                      // 209
    Shops("Fitcity", 2,263f,588f),                          // 210
    Shops("Mediamarkt", 2,260f,685f),                       // 212
    Shops("Molasera", 2,176f,664f),                         // 213
    Shops("Tavuk Dünyası", 2,176f,612f),                    // 214
    Shops("Green Salads", 2,130f,546f),                     // 215
    Shops("GurmeBurger Kasap", 2,153f,454f),                // 216 - 217
    Shops("Bavra Pide", 2,153f,396f),                       // 218
    Shops("Köfteci Ramiz", 2,153f,355f),                    // 219
    Shops("Makarnam", 2,153f,318f),                         // 220
    Shops("Dürümle", 2,175f,280f),                          // 221
    Shops("Burger King", 2,202f,242f),                      // 222
    Shops("Mado", 2,276f,220f),                             // 223
    Shops("Usta Dönerci", 2,338f,207f),                     // 224
    Shops("Popeyes", 2,408f,207f),                          // 225
    Shops("Arby's", 2,478f,207f),                           // 226
    Shops("KFC", 2,680f,193f),                              // 228
    // Red Points
    Shops("Direct Protect", 2,546f,237f),                   // 229

    // Ground Floor Shops
    Shops("Midpoint", 0,760f,182f),   //Z-01
    Shops("Marks&Spencer", 0,759f,247f),   //Z-02
    Shops("Saat&Saat", 0,703f,297f),   //Z-03
    Shops("Divarese", 0,710f,367f),   //Z-04
    Shops("Suwen", 0,710f,407f),   //Z-05
    Shops("Beymen Club", 0,710f,446f),   //Z-06
    Shops("English Home", 0,759f,486f),   //Z-07
    Shops("Tüzün", 0,776f,526f),   //Z-08
    Shops("Kosmika", 0,737f,562f),   //Z-09
    Shops("Faik Sönmez", 0,637f,570f),   //Z-10
    Shops("Network Men", 0,498f,535f),   //Z-11
    Shops("Tommy Hilfiger", 0,387f,535f),   //Z-12
    Shops("Blue Diamond", 0,284f,559f),   //Z-13
    Shops("Pierre Cardin", 0,284f,658f),   //Z-14
    Shops("W Collection", 0,284f,719f),   //Z-15
    Shops("Madame Coco", 0,284f,781f),   //Z-16
    Shops("Paşabahçe Mağazaları", 0,231f,821f),   //Z-17
    Shops("Pelit", 0,176f,791f),   //Z-18
    Shops("Massimo Dutti", 0,176f,685f),   //Z-19
    Shops("Tobacco Shop", 0,44f,712f),   //Z-20
    Shops("Cacharel", 0,107f,582f),   //Z-21
    Shops("Derimod", 0,105f,514f),   //Z-22
    Shops("Avva", 0,121f,443f),   //Z-23
    Shops("Hotiç", 0,121f,403f),   //Z-24
    Shops("Network Women", 0,121f,326f),   //Z-25
    Shops("Lizay", 0,121f,256f),   //Z-26
    Shops("Damat Tween", 0,121f,222f),   //Z-27
    Shops("Hugo Boss", 0,239f,175f),   //Z-28
    Shops("Cookshop", 0,359f,175f),   //Z-29
    Shops("Chocolate", 0,499f,175f),   //Z-30
    Shops("Mac", 0,599f,175f),   //Z-31
    Shops("Capacity Eczane", 0,663f,157f),   //Z-32
    Shops("Atasay", 0,625f,265f),   //Z-33
    Shops("SuperStep", 0,652f,323f),   //Z-34
    Shops("Utopian", 0,651f,406f),   //Z-36
    Shops("ipekyol", 0,610f,474f),   //Z-37
    Shops("Twist", 0,480f,503f),   //Z-38
    Shops("Mocassini", 0,403f,503f),   //Z-39
    Shops("So Chic", 0,330f,503f),   //Z-40
    Shops("Tanca Plus", 0,287f,449f),   //Z-41
    Shops("Ramsey", 0,286f,406f),   //Z-42
    Shops("Vakko", 0,287f,366f),   //Z-43
    Shops("Zen", 0,330f,323f),   //Z-44
    Shops("LuaPie", 0,403f,281f),   //Z-45
    Shops("Lacoste", 0,515f,255f),   //Z-46

    // -1 Basement Floor
    Shops("Koton", -1,811f,212f),                           // B-01
    Shops("Atelier Rebul", -1,770f,265f),                   // B-02
    Shops("Jumbo", -1,738f,299f),                           // B-03
    Shops("Yatsan", -1,738f,385f),                          // B-05
    Shops("Watsons", -1,738f,416f),                         // B-06
    Shops("Nike", -1,796f,508f),                            // B-07
    Shops("Phone Hospital", -1,788f,558f),                  // B-08
    Shops("Vodafone", -1,752f,576f),                        // B-09
    Shops("Turkcell-Genpa", -1,704f,581f),                  // B-10
    Shops("Ender Spor", -1,580f,545f),                      // B-11
    Shops("Eve", -1,509f,545f),                             // B-12
    Shops("Atasun Optik", -1,442f,545f),                    // B-13
    Shops("Safranbolu Lokumcusu", -1,326f,557f),            // B-14
    Shops("ETS Turizm", -1,326f,598f),                      // B-15
    Shops("Jolly Tur", -1,326f,640f),                       // B-16
    Shops("Migros MMM", -1,249f,660f),                      // B-17
    Shops("Şenol Zeytinoğlu - Kadın", -1,172f,640f),        // B-18
    Shops("Linens", -1,172f,602f),                          // B-19
    Shops("Panista", -1,172f,555f),                         // B-20
    Shops("B&G Store", -1,172f,491f),                       // B-21
    Shops("Penti", -1,172f,409f),                           // B-22
    Shops("Kifidis", -1,172f,332f),                         // B-24
    Shops("Mamino", -1,172f,298f),                          // B-25
    Shops("Toyzz Shop", -1,172f,270f),                      // B-26
    Shops("Armağan Oyuncak", -1,253f,223f),                 // B-27
    Shops("Flo", -1,315f,188f),                             // B-28
    Shops("Ceyo", -1,393f,176f),                            // B-29
    Shops("Korkmaz", -1,453f,176f),                         // B-30
    Shops("Arçelik", -1,525f,176f),                         // B-31
    Shops("Samsung", -1,593f,176f),                         // B-32
    Shops("Tefal", -1,673f,176f),                           // B-33
    Shops("Home Sweet Home", -1,722f,168f),                 // B-34
    Shops("LC Waikiki", -1,417f,307f),                      // B-35
    Shops("Columbia", -1,669f,373f),                        // B-36
    Shops("The North Face", -1,669f,409f),                  // B-37
    Shops("Adidas", -1,669f,452f),                          // B-38
    Shops("Kartal Yuvası", -1,512f,504f),                   // B-39
    Shops("Panço", -1,333f,497f),                           // B-40
    Shops("Tchibo", -1,327f,447f),                          // B-41
    Shops("Ayakkabı Dünyası", -1,327f,394f),                // B-42

    //Red points
    Shops("Çiğköftem", -1,283f,578f),                       // B-43
    Shops("Haribo Çok Şeker", -1,215f,578f),                // B-44
    Shops("Aydın Döviz & Altın", -1,180f,523f),             // B-45
    Shops("Mad Parfumeur", -1,250f,478f),                   // B-46
    Shops("Malatya Pazarı Palancı", -1,251f,447f),          // B-47
    Shops("Arifoğlu", -1,250f,416f),                        // B-48
    Shops("Krispy Kreme", -1,251f,385f),                    // B-49
    Shops("Cafe Poi", -1,250f,356f),                        // B-50
    Shops("Sihirli Eller", -1,553f,219f),                   // B-51
    Shops("Rainwater", -1,710f,182f),                       // B-52
    Shops("Akıl ve Zeka Oyunları", -1,705f,270f),           // B-53
    Shops("Cezve Kahve", -1,625f,525f),                     // B-54

)