package model;

// هذا الكلاس يمثل كيان "فيلم" Movie ويحتوي على خصائص ومعلومات متعلقة بالفيلم
public class Movie {

    // الخصائص (Attributes) الخاصة بالفيلم
    private int id;                       // معرف الفيلم (رقم فريد)
    private String title;                // عنوان الفيلم
    private String releaseDate;          // تاريخ إصدار الفيلم
    private double rating;               // تقييم الفيلم
    private String description;          // وصف الفيلم
    private String imageUrl;             // رابط صورة البوستر
    private String trailerUrl;           // رابط عرض الفيديو التشويقي (Trailer)

    // ✅ خاصية إضافية لمعرفة هل الفيلم مفضل من قبل المستخدم الحالي
    private boolean favorited;

    // المُنشئ الفارغ (بدون معطيات) – ضروري لبعض الأطر (frameworks)
    public Movie() {
    }

    // مُنشئ بكل الخصائص – يُستخدم عند إنشاء كائن جديد يحتوي على جميع القيم
    public Movie(int id, String title, String imageUrl, String releaseDate, double rating, String description, String trailerUrl) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.description = description;
        this.trailerUrl = trailerUrl;
    }

    // =============================
    // ✅ Getters – لاسترجاع قيم الخصائص
    // =============================

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getImageUrl() { return imageUrl; }
    public String getReleaseDate() { return releaseDate; }
    public double getRating() { return rating; }
    public String getDescription() { return description; }
    public String getTrailerUrl() { return trailerUrl; }

    // =============================
    // ✅ Setters – لتعيين قيم الخصائص
    // =============================

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }
    public void setRating(double rating) { this.rating = rating; }
    public void setDescription(String description) { this.description = description; }
    public void setTrailerUrl(String trailerUrl) { this.trailerUrl = trailerUrl; }

    // ⚠️ ميثود غير مكتمل تم توليده تلقائيًا ولم يُستخدم على الأرجح
    public void setPosterUrl(String string) {
        throw new UnsupportedOperationException("Not supported yet."); 
        // يُفضل حذف هذا الميثود أو تنفيذه بشكل صحيح إذا كان ضروريًا
    }

    // =============================
    // ✅ Getter و Setter للخاصية favorited
    // =============================

    // يُستخدم لمعرفة ما إذا كان الفيلم مفضلًا
    public boolean isFavorited() {
        return favorited;
    }

    // يُستخدم لتعيين حالة التفضيل للفيلم
    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }
}
