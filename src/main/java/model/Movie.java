package model;

public class Movie {
    private int id;
    private String title;
    private String releaseDate;
    private double rating;
    private String description;
    private String imageUrl;
    private String trailerUrl;
    
    // ✅ خاصية لمعرفة ما إذا كان الفيلم مفضلًا من قبل المستخدم
    private boolean favorited;

    // المنشئ الفارغ
    public Movie() {
    }

    // منشئ بكل الخصائص
    public Movie(int id, String title, String imageUrl, String releaseDate, double rating, String description, String trailerUrl) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.description = description;
        this.trailerUrl = trailerUrl;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getImageUrl() { return imageUrl; }
    public String getReleaseDate() { return releaseDate; }
    public double getRating() { return rating; }
    public String getDescription() { return description; }
    public String getTrailerUrl() { return trailerUrl; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }
    public void setRating(double rating) { this.rating = rating; }
    public void setDescription(String description) { this.description = description; }
    public void setTrailerUrl(String trailerUrl) { this.trailerUrl = trailerUrl; }

    public void setPosterUrl(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // ✅ Getter و Setter للـ favorited
    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }
}
