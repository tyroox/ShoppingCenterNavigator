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
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingcenternavigator.ui.theme.caribbeanCurrent
import com.example.shoppingcenternavigator.ui.theme.skyBlue

@Composable
fun StoreSearchBar(
    variables: List<Shops>,
    onSearch: (String) -> Unit,
    onBoxClick: (Int) -> Unit
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
        "watson's" to R.drawable.watsons_logo,
        "workinton" to R.drawable.workinton_logo,
        "yves rocher" to R.drawable.yves_rocher_logo
    )

    Column(Modifier.fillMaxWidth()) {
        // Search bar
        TextField(
            value = searchQuery,
            onValueChange = { query ->
                searchQuery = query
                onSearch(query)
            },
            placeholder = { Text("Ara", color = caribbeanCurrent) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.isabelline),
                disabledTextColor = Color.Black,
                cursorColor = colorResource(id = R.color.caribbeanCurrent),
                unfocusedIndicatorColor = colorResource(id = R.color.caribbeanCurrent),
                focusedIndicatorColor = colorResource(id = R.color.caribbeanCurrent)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

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
                variable.Name.toLowerCase().contains(searchQuery.toLowerCase())
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
                                backgroundImages[variable.Name.toLowerCase()]
                            val backgroundPainter = backgroundResource?.let { painterResource(it) }

                            Box(
                                modifier = Modifier
                                    .padding(vertical = 4.dp)
                                    .size(boxSize)
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(skyBlue.copy(alpha = 0.25f))
                                    .clickable {
                                        onBoxClick(index)
                                    }
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize().padding(8.dp),
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
                                            text = "Zemin Kat",
                                            color = Color.Black,
                                            fontSize = 20.sp,
                                            textAlign = TextAlign.Center
                                        )
                                    } else {
                                        Text(
                                            text = variable.Floor.toString() + ". Kat",
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