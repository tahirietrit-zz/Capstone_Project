package objects;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by macb on 12/05/16.
 */
public class Item {
    public static String REPORT_TABLE = "reports";
    public static String id_ = "id";
    public static String title_ = "title";
    public static String description_ = "description";
    public static String image_ = "image";
    public static String created_by_ = "created_by";
    public static String created_at_ = "created_at";
    public static String updated_at_ = "updated_at";

    public static final String CONTENT_AUTHORITY = "com.tahirietrit.ireport";
    public static final String PATH = "reports";
    public static Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

    public static Uri buildWithLastTrip() {
        return BASE_CONTENT_URI.buildUpon().appendPath("id").build();
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("created_by")
    @Expose
    private Integer createdBy;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("creator")
    @Expose
    private Creator creator;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     *
     * @return
     * The createdBy
     */
    public Integer getCreatedBy() {
        return createdBy;
    }

    /**
     *
     * @param createdBy
     * The created_by
     */
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     * The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     *
     * @return
     * The creator
     */
    public Creator getCreator() {
        return creator;
    }

    /**
     *
     * @param creator
     * The creator
     */
    public void setCreator(Creator creator) {
        this.creator = creator;
    }
    public ContentValues getContentValues() {

        ContentValues values = new ContentValues();
        values.put(Item.id_, id);
        values.put(Item.title_, title);
        values.put(Item.description_, description);
        values.put(Item.image_, image);
        values.put(Item.created_by_, createdBy);
        values.put(Item.created_at_, createdAt);
        values.put(Item.updated_at_, updatedAt);

        return values;
    }

}