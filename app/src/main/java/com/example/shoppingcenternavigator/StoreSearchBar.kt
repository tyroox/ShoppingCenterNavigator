package com.example.shoppingcenternavigator

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shoppingcenternavigator.ui.theme.caribbeanCurrent
import com.example.shoppingcenternavigator.ui.theme.skyBlue
import com.example.shoppingcenternavigator.ui.theme.wineBerry
import java.util.Locale

@Composable
fun StoreSearchBar(
    variables: List<Shops>,
    onSearch: (String) -> Unit,
    onBoxClick: (String) -> Unit,
    selectedItem: MutableState<Int>
) {

    var searchQuery by remember { mutableStateOf("") }

    // Define the map associating text with background image resources
    val backgroundImages = mapOf(
        "acıbadem hastanesi" to R.drawable.acibadem_logo,
        "adabella pizza" to R.drawable.adabella_pizza_logo,
        "adl" to R.drawable.adl_logo,
        "alaçatı muhallebicisi" to R.drawable.alacati_muhallebicisi_logo,
        "arby's" to R.drawable.arbys_logo,
        "armağan oyuncak" to R.drawable.armagan_oyuncak_logo,
        "atasun optik" to R.drawable.atasun_logo,
        "barok diamond" to R.drawable.barok_diamond_logo,
        "batik" to R.drawable.batik_logo,
        "beko" to R.drawable.beko_logo,
        "united colors of benetton" to R.drawable.benetton_logo,
        "bücürük" to R.drawable.bucuruk_logo,
        "burger king" to R.drawable.burger_king_logo,
        "bursa kebap evi" to R.drawable.bursa_kebap_evi_logo,
        "cafe vienna" to R.drawable.cafe_vienna_logo,
        "chinese & sushi express" to R.drawable.chinese_sushi_logo,
        "cici fırın" to R.drawable.cici_firin_logo,
        "cinema pink" to R.drawable.cinema_pink_logo,
        "colin's" to R.drawable.colins_logo,
        "the cookshop" to R.drawable.cookshop_logo,
        "coral travel" to R.drawable.coral_travel_logo,
        "creaphone" to R.drawable.creaphone_logo,
        "dagi" to R.drawable.dagi_logo,
        "decathlon" to R.drawable.decathlon_logo,
        "defacto" to R.drawable.defacto_logo,
        "deichmann" to R.drawable.deichmann_logo,
        "derimod" to R.drawable.derimod_logo,
        "desa" to R.drawable.desa_logo,
        "dimalis" to R.drawable.dimalis_logo,
        "diva" to R.drawable.diva_logo,
        "d&r" to R.drawable.dr_logo,
        "dry center" to R.drawable.dry_center_logo,
        "d's damat" to R.drawable.ds_damat_logo,
        "ebebek" to R.drawable.ebebek_logo,
        "ecrou" to R.drawable.ecrou_logo,
        "efor" to R.drawable.efor_logo,
        "ets turizm" to R.drawable.ets_tur_logo,
        "fizyoheal" to R.drawable.fizyoheal_logo,
        "focaccia" to R.drawable.focaccia_logo,
        "frederic patric parfum" to R.drawable.frederic_patric_parfum_logo,
        "gratis" to R.drawable.gratis_logo,
        "gs store" to R.drawable.gs_store_logo,
        "gusto" to R.drawable.gusto_logo,
        "gymotion" to R.drawable.gymotion_logo,
        "hayal kahvesi" to R.drawable.hayal_kahvesi_logo,
        "hotiç" to R.drawable.hotic_logo,
        "hummel" to R.drawable.hummel_logo,
        "igs" to R.drawable.igs_logo,
        "istikbal yatak" to R.drawable.istikbal_yatak_logo,
        "jakamen" to R.drawable.jakamen_logo,
        "jepublic" to R.drawable.jepublic_logo,
        "jolly tur" to R.drawable.jolly_tur_logo,
        "journey" to R.drawable.journey_logo,
        "joy park" to R.drawable.joy_park_logo,
        "jument" to R.drawable.jument_logo,
        "junior chef" to R.drawable.junior_chef_logo,
        "just chik'n" to R.drawable.just_chikn_logo,
        "karaca" to R.drawable.karaca_logo,
        "kemal tanca" to R.drawable.kemal_tanca_logo,
        "kiğılı" to R.drawable.kigili_logo,
        "köfteci ramiz" to R.drawable.kofteci_ramiz_logo,
        "leman kültür" to R.drawable.leman_kultur_logo,
        "linens" to R.drawable.linens_logo,
        "love my body" to R.drawable.love_my_body_logo,
        "mado" to R.drawable.mado_logo,
        "magic play" to R.drawable.magic_play_logo,
        "mavi" to R.drawable.mavi_logo,
        "migros" to R.drawable.migros_logo,
        "mr. diy" to R.drawable.mr_diy_logo,
        "mudo collection" to R.drawable.mudo_collection_logo,
        "özdilek" to R.drawable.ozdilek_logo,
        "penti" to R.drawable.penti_logo,
        "perspective" to R.drawable.perspective_logo,
        "pierre cardin" to R.drawable.pierre_cardin_logo,
        "u.s. polo assn." to R.drawable.polo_logo,
        "rock n roll" to R.drawable.rock_n_roll_logo,
        "saat&saat" to R.drawable.saat_saat_logo,
        "salads mixx" to R.drawable.salads_mixx_logo,
        "sevil" to R.drawable.sevil_logo,
        "siemens" to R.drawable.siemens_logo,
        "starbucks" to R.drawable.starbucks_logo_png,
        "tam sos döner" to R.drawable.tam_sos_doner_logo,
        "toyzz shop" to R.drawable.toyzz_shop_logo,
        "turkcell" to R.drawable.turkcell_logo,
        "vakko" to R.drawable.vakko_logo,
        "vodafone" to R.drawable.vodafone_logo,
        "w collection men" to R.drawable.w_collection_logo,
        "w collection women" to R.drawable.w_collection_logo,
        "watsons" to R.drawable.watsons_logo,
        "workinton" to R.drawable.workinton_logo,
        "yves rocher" to R.drawable.yves_rocher_logo,

        // Capacity Shops
        "adidas" to R.drawable.adidas_logo,
        "akıl ve zeka oyunları" to R.drawable.akil_ve_zeka_oyunlari_logo,
        "arçelik" to R.drawable.arcelik_logo,
        "arifoğlu" to R.drawable.arifoglu_logo,
        "atasay" to R.drawable.atasay_logo,
        "atelier rebul" to R.drawable.atelier_rebul_logo,
        "avon" to R.drawable.avon_logo,
        "avva" to R.drawable.avva_logo,
        "ayakkabı dünyası" to R.drawable.ayakkabi_dunyasi_logo,
        "aydın döviz & altın" to R.drawable.aydin_doviz_altin_logo,
        "b&g store" to R.drawable.b_g_store_logo,
        "bavra pide" to R.drawable.bavra_pide_logo,
        "bershka" to R.drawable.bershka_logo,
        "beymen club" to R.drawable.beymen_club_logo,
        "birkenstock" to R.drawable.birkenstock_logo,
        "blue diamond" to R.drawable.blue_diamond_logo,
        "boble bubble tea" to R.drawable.boble_bubble_tea_logo,
        "cacharel" to R.drawable.cacharel_logo,
        "cafe poi" to R.drawable.cafe_poi_logo,
        "calzedonia" to R.drawable.calzedonia_logo,
        "camper" to R.drawable.camper_logo,
        "capacity eczane" to R.drawable.capacity_eczane_logo,
        "cella" to R.drawable.cella_logo,
        "ceyo" to R.drawable.ceyo_logo,
        "cezve kahve" to R.drawable.cezve_kahve_logo,
        "chocolate" to R.drawable.chocolate_logo,
        "çiğköftem" to R.drawable.cigkoftem_logo,
        "columbia" to R.drawable.columbia_logo,
        "cookshop" to R.drawable.cookshop_logo,
        "damat tween" to R.drawable.damat_tween_logo,
        "direct protect" to R.drawable.direct_protect_logo,
        "divarese" to R.drawable.divarese_logo,
        "dürümle" to R.drawable.durumle_logo,
        "elle" to R.drawable.elle_logo,
        "ender spor" to R.drawable.ender_spor_logo,
        "english home" to R.drawable.english_home_logo,
        "eve" to R.drawable.eve_logo,
        "faik sönmez" to R.drawable.faik_sonmez_logo,
        "fitcity" to R.drawable.fitcity_logo,
        "flo" to R.drawable.flo_logo,
        "flormar" to R.drawable.flormar_logo,
        "gloria jeans coffees" to R.drawable.gloria_jeans_logo,
        "gmg firenze" to R.drawable.gmg_firenze_logo,
        "golden rose" to R.drawable.golden_rose_logo,
        "gözgrup optik" to R.drawable.gozgrup_optik_logo,
        "green salads" to R.drawable.green_salads_logo,
        "greyder" to R.drawable.greyder_logo,
        "günaydın" to R.drawable.gunaydin_logo,
        "gurmeburger kasap" to R.drawable.gurmeburger_kasap_logo,
        "haribo çok şeker" to R.drawable.haribo_logo,
        "hd iskender" to R.drawable.hd_iskender_logo,
        "home sweet home" to R.drawable.home_sweet_home_logo,
        "hugo boss" to R.drawable.hugo_boss_logo,
        "intimissimi" to R.drawable.intimissimi_logo,
        "ipekyol" to R.drawable.ipekyol_logo,
        "jimmy key" to R.drawable.jimmy_key_logo,
        "jumbo" to R.drawable.jumbo_logo,
        "kartal yuvası" to R.drawable.kartal_yuvasi_logo,
        "kfc" to R.drawable.kfc_logo,
        "kifidis" to R.drawable.kifidis_logo,
        "kiko" to R.drawable.kiko_logo,
        "konyalı saat" to R.drawable.konyali_saat_logo,
        "korkmaz" to R.drawable.korkmaz_logo,
        "kosmika" to R.drawable.kosmika_logo,
        "koton" to R.drawable.koton_logo,
        "krispy kreme" to R.drawable.krispy_kreme_logo,
        "lacoste" to R.drawable.lacoste_logo,
        "lc waikiki" to R.drawable.lc_waikiki_logo,
        "lee wrangler" to R.drawable.lee_wrangler_logo,
        "lelas" to R.drawable.lelas_logo,
        "lizay" to R.drawable.lizay_logo,
        "l'occitane" to R.drawable.loccitane_logo,
        "loris" to R.drawable.loris_logo,
        "ltb" to R.drawable.ltb_logo,
        "luapie" to R.drawable.luapie_logo,
        "lufian" to R.drawable.lufian_logo,
        "mac" to R.drawable.mac_logo,
        "mad parfumeur" to R.drawable.mad_parfumeur_logo,
        "madame coco" to R.drawable.madame_coco_logo,
        "makarnam" to R.drawable.makarnam_logo,
        "malatya pazarı palancı" to R.drawable.malatya_pazari_logo,
        "mamino" to R.drawable.mamino_logo,
        "mango" to R.drawable.mango_logo,
        "marks&spencer" to R.drawable.marks_spencer_logo,
        "massimo dutti" to R.drawable.massimo_dutti_logo,
        "mediamarkt" to R.drawable.media_markt_logo,
        "mi store" to R.drawable.mi_store_logo,
        "midpoint" to R.drawable.midpoint_logo,
        "migros mmm" to R.drawable.migros_logo,
        "mocassini" to R.drawable.mocassini_logo,
        "molasera" to R.drawable.molasera_logo,
        "network men" to R.drawable.network_logo,
        "network women" to R.drawable.network_logo,
        "nike" to R.drawable.nike_logo,
        "oxxo" to R.drawable.oxxo_logo,
        "oysho" to R.drawable.oysho_logo,
        "oyun zamanı" to R.drawable.oyun_zamani_logo,
        "özsüt" to R.drawable.ozsut_logo,
        "panço" to R.drawable.panco_logo,
        "pandora" to R.drawable.pandora_logo,
        "panista" to R.drawable.panista_logo,
        "paribu cineverse" to R.drawable.paribu_cineverse_logo,
        "paşabahçe mağazaları" to R.drawable.pasabahce_logo,
        "pelit" to R.drawable.pelit_logo,
        "phone hospital" to R.drawable.phone_hospital_logo,
        "pidem" to R.drawable.pidem_logo,
        "popeyes" to R.drawable.popeyes_logo,
        "pull&bear" to R.drawable.pull_bear_logo,
        "rainwater" to R.drawable.rainwater_logo,
        "ramsey" to R.drawable.ramsey_logo,
        "safranbolu lokumcusu" to R.drawable.safranbolu_lokumcusu_logo,
        "samsonite" to R.drawable.samsonite_logo,
        "samsung" to R.drawable.samsung_logo,
        "şenol zeytinoğlu - kadın" to R.drawable.senol_zeytinoglu_logo,
        "sephora" to R.drawable.sephora_logo,
        "sihirli eller" to R.drawable.sihirli_eller_logo,
        "skechers" to R.drawable.skechers_logo,
        "sneaks up" to R.drawable.sneaks_up_logo,
        "so chic" to R.drawable.so_chic_logo,
        "stradivarius" to R.drawable.stradivarius_logo,
        "stylish" to R.drawable.stylish_logo,
        "sunglass hut / ray-ban" to R.drawable.sunglass_hut_logo,
        "superstep" to R.drawable.superstep_logo,
        "suwen" to R.drawable.suwen_logo,
        "swarovski" to R.drawable.swarovski_logo,
        "tanca plus" to R.drawable.tanca_plus_logo,
        "tavuk dünyası" to R.drawable.tavuk_dunyasi_logo,
        "tchibo" to R.drawable.tchibo_logo,
        "tefal" to R.drawable.tefal_logo,
        "the north face" to R.drawable.the_north_face_logo,
        "timberland" to R.drawable.timberland_logo,
        "tobacco shop" to R.drawable.tobacco_shop_logo,
        "tommy hilfiger" to R.drawable.tommy_hilfiger_logo,
        "turkcell-genpa" to R.drawable.turkcell_logo,
        "tüzün" to R.drawable.tuzun_logo,
        "twist" to R.drawable.twist_logo,
        "usta dönerci" to R.drawable.usta_donerci_logo,
        "utopian" to R.drawable.utopian_logo,
        "vodafone-cep point" to R.drawable.vodafone_logo,
        "w collection" to R.drawable.w_collection_logo,
        "yatsan" to R.drawable.yatsan_logo,
        "zara" to R.drawable.zara_logo,
        "zen" to R.drawable.zen_logo,

        )

    Column(Modifier.fillMaxWidth()) {
        // Search bar
        TextField(
            value = searchQuery,
            onValueChange = { query ->
                searchQuery = query
                onSearch(query)
            },
            placeholder = { Text(stringResource(id = R.string.searchStoreButton), color = wineBerry) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = wineBerry,
                unfocusedIndicatorColor = wineBerry,
                focusedIndicatorColor = wineBerry
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Row(Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            OutlinedButton(onClick = { selectedItem.value = 5 },
                colors = ButtonDefaults.buttonColors(backgroundColor = skyBlue.copy(alpha = 0.25f),
                    contentColor = wineBerry)) {
                Text(text = stringResource(id = R.string.floorPlans))
            }
        }

        // Box-shaped items
        val screenWidth = with(LocalDensity.current) {
            LocalConfiguration.current.screenWidthDp.dp
        }
        val boxSize = with(LocalDensity.current) {
            screenWidth / 2 - 16.dp // Adjust margin as needed
        }

        val filteredVariables = if (searchQuery.isEmpty()) {
            variables
        } else {
            variables.filter { variable ->
                variable.Name.lowercase(Locale.getDefault()).contains(searchQuery.lowercase(Locale.getDefault()))
            }
        }

        val numRows = (filteredVariables.size + 1) / 2

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {

            items(numRows) { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    for (col in 0 until 2) {
                        val index = row * 2 + col
                        if (index < filteredVariables.size) {
                            val variable = filteredVariables[index]
                            val backgroundResource =
                                backgroundImages[variable.Name.lowercase(Locale.getDefault())]
                            val backgroundPainter = backgroundResource?.let { painterResource(it) }

                            Box(
                                modifier = Modifier
                                    .padding(vertical = 4.dp)
                                    .size(boxSize)
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(skyBlue.copy(alpha = 0.25f))
                                    .clickable {
                                        onBoxClick(variable.Name)
                                    }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(8.dp),
                                    verticalArrangement = Arrangement.Top,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    if (backgroundPainter != null) {
                                        Image(
                                            painter = backgroundPainter,
                                            contentDescription = null,
                                            contentScale = ContentScale.Inside,
                                            modifier = Modifier
                                                .size(100.dp)
                                                .clip(RectangleShape)
                                        )
                                    }
                                }
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(bottom = 16.dp),
                                    verticalArrangement = Arrangement.Bottom,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = variable.Name,
                                        color = Color.Black,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    )
                                    if (variable.Floor == 0) {
                                        Text(
                                            text = stringResource(id = R.string.groundFloor),
                                            color = Color.Black,
                                            fontSize = 20.sp,
                                            textAlign = TextAlign.Center
                                        )
                                    } else {
                                        Text(
                                            text = variable.Floor.toString() + stringResource(id = R.string.floor),
                                            color = Color.Black,
                                            fontSize = 20.sp,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                    }
                        else {
                            Spacer(Modifier.size(boxSize))
                        }
                }
            }
        }
    }
    }
}