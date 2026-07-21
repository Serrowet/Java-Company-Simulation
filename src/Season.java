public enum Season {
    WINTER("Winter", "❄️"),
    SPRING("Spring", "🌸"),
    SUMMER("Summer", "☀️"),
    AUTUMN("Autumn", "🍂");

    private final String name;
    private final String icon;

    Season(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }
    public String getName() {
        return name;
    }
    public String getIcon() {
        return icon;
    }
}
