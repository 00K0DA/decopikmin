package com.oukoda.decopikmin.enum

import com.oukoda.decopikmin.R

enum class DecorType {
    Restaurant,
    Cafe,
    Sweetshop,
    MovieTheater,
    Pharmacy,
    Zoo,
    Forest,
    Waterside,
    PostOffice,
    ArtGallery,
    Airport,
    Station,
    Beach,
    BurgerPlace,
    CornerStore,
    Supermarket,
    Bakery,
    HairSalon,
    ClothesStore,
    Park,
    LibraryAndBookstore,
    Special,
    LoadSide,
    SushiRestaurant,
    Mountain,
    Weather,
    ThemePark,
    BusStop;

    companion object {
        fun getCostumes(decorType: DecorType): List<Costume> {
            return when (decorType) {
                Restaurant -> listOf(Costume.Chef, Costume.ShinyChef)
                Cafe -> listOf(Costume.CoffeeCup)
                Sweetshop -> listOf(Costume.Macaron)
                MovieTheater -> listOf(Costume.PopcornSnack)
                Pharmacy -> listOf(Costume.Toothbrush)
                Zoo -> listOf(Costume.Dandelion)
                Forest -> listOf(Costume.StagBeetle, Costume.Acorn)
                Waterside -> listOf(Costume.FishingLure)
                PostOffice -> listOf(Costume.Stamp)
                ArtGallery -> listOf(Costume.PictureFrame)
                Airport -> listOf(Costume.ToyAirPlane)
                Station -> listOf(Costume.PaperTrain, Costume.Ticket)
                Beach -> listOf(Costume.Shell)
                BurgerPlace -> listOf(Costume.Burger)
                CornerStore -> listOf(Costume.BottleCap, Costume.Snack)
                Supermarket -> listOf(Costume.Mushroom, Costume.Banana)
                Bakery -> listOf(Costume.Baguette)
                HairSalon -> listOf(Costume.Scissors)
                ClothesStore -> listOf(Costume.HairTie)
                Park -> listOf(Costume.Clover, Costume.FourLeafClover)
                LibraryAndBookstore -> listOf(Costume.TinyBook)
                Special ->
                    listOf(
                        Costume.Mario,
                        Costume.NewYear,
                        Costume.Chess,
                        Costume.FingerBoard,
                        Costume.FlowerCard,
                        Costume.JackOLantern
                    )
                LoadSide -> listOf(Costume.Sticker)
                SushiRestaurant -> listOf(Costume.Sushi)
                Mountain -> listOf(Costume.MountainPinBadge)
                Weather -> listOf(Costume.LeafHat)
                ThemePark -> listOf(Costume.ThemeParkTicket)
                BusStop -> listOf(Costume.BusPaperCraft)
            }
        }

        fun getDecorText(decorType: DecorType): Int {
            return when (decorType) {
                Restaurant -> R.string.decor_type_restaurant
                Cafe -> R.string.decor_type_cafe
                Sweetshop -> R.string.decor_type_sweetshop
                MovieTheater -> R.string.decor_type_movie_theater
                Pharmacy -> R.string.decor_type_pharmacy
                Zoo -> R.string.decor_type_zoo
                Forest -> R.string.decor_type_forest
                Waterside -> R.string.decor_type_waterside
                PostOffice -> R.string.decor_type_post_office
                ArtGallery -> R.string.decor_type_art_gallery
                Airport -> R.string.decor_type_airport
                Station -> R.string.decor_type_station
                Beach -> R.string.decor_type_beach
                BurgerPlace -> R.string.decor_type_burger_place
                CornerStore -> R.string.decor_type_corner_store
                Supermarket -> R.string.decor_type_supermarket
                Bakery -> R.string.decor_type_bakery
                HairSalon -> R.string.decor_type_hair_salon
                ClothesStore -> R.string.decor_type_clothes_store
                Park -> R.string.decor_type_park
                LibraryAndBookstore -> R.string.decor_type_library_and_bookstore
                Special -> R.string.decor_type_special
                LoadSide -> R.string.decor_type_load_side
                SushiRestaurant -> R.string.decor_type_sushi_restaurant
                Mountain -> R.string.decor_type_mountain
                Weather -> R.string.decor_type_weather
                ThemePark -> R.string.decor_type_theme_park
                BusStop -> R.string.decor_type_bus_stop
            }
        }
    }
}
