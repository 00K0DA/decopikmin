package com.oukoda.decopikmin.myclass

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
    ThemePark;

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
                    listOf(Costume.Mario, Costume.NewYear, Costume.Chess, Costume.FingerBoard)
                LoadSide -> listOf(Costume.Sticker)
                SushiRestaurant -> listOf(Costume.Sushi)
                Mountain -> listOf(Costume.MountainPinBadge)
                Weather -> listOf(Costume.LeafHat)
                ThemePark -> listOf(Costume.ThemeParkTicket)
            }
        }
    }
}
