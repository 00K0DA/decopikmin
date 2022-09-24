package com.oukoda.decopikmin.myclass

enum class Costume(val value: String) {
    Chef("Chef"),
    ShinyChef("ShinyChef"),
    CoffeeCup("CoffeeCup"),
    Macaron("Macaron"),
    PopcornSnack("PopcornSnack"),
    Toothbrush("Toothbrush"),
    Dandelion("Dandelion"),
    StagBeetle("StagBeetle"),
    Acorn("Acorn"),
    FishingLure("FishingLure"),
    Stamp("Stamp"),
    PictureFrame("PictureFrame"),
    ToyAirPlane("ToyAirPlane"),
    PaperTrain("PaperTrain"),
    Ticket("Ticket"),
    Shell("Shell"),
    Burger("Burger"),
    BottleCap("BottleCap"),
    Snack("Snack"),
    Mushroom("Mushroom"),
    Banana("Banana"),
    Baguette("Baguette"),
    Scissors("Scissors"),
    HairTie("HairTie"),
    Clover("Clover"),
    FourLeafClover("FourLeafClover"),
    TinyBook("TinyBook"),
    Sushi("Sushi"),
    MountainPinBadge("MountainPinBadge"),

    // Weather
    LeafHat("LeafHat"),

    // LoadSide
    Sticker("Sticker"),

    // Special
    Mario("Mario"),
    NewYear("NewYear"),
    Chess("Chess"),
    FingerBoard("FingerBoard"),

    ThemeParkTicket("ThemeParkTicket");


    companion object {
        fun getPikminList(costume: Costume): List<PikminType> {
            return when (costume) {
                Mario -> listOf(PikminType.Red)
                Chess ->
                    listOf(PikminType.Yellow, PikminType.Blue, PikminType.White, PikminType.Purple)
                LeafHat -> listOf(PikminType.Blue, PikminType.Blue, PikminType.Blue)
                ShinyChef, NewYear, MountainPinBadge, Sushi, ThemeParkTicket ->
                    listOf(PikminType.Red, PikminType.Blue, PikminType.Yellow)
                FingerBoard ->
                    listOf(PikminType.Red, PikminType.Yellow, PikminType.Purple, PikminType.Wing)
                else -> PikminType.values().toList()
            }
        }

        fun getAllPikminCount(): Int = values().sumOf { getPikminList(it).size }
    }
}
