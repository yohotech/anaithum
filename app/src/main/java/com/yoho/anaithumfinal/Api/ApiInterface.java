package com.yoho.anaithumfinal.Api;

import com.yoho.anaithumfinal.Model.ActiveOrderModel;
import com.yoho.anaithumfinal.Model.AddAreaModel;
import com.yoho.anaithumfinal.Model.AddCityModel;
import com.yoho.anaithumfinal.Model.AddPincodeModel;
import com.yoho.anaithumfinal.Model.AddStateModel;
import com.yoho.anaithumfinal.Model.AddressResponse;
import com.yoho.anaithumfinal.Model.ApplyCouponModel;
import com.yoho.anaithumfinal.Model.BannerModel;
import com.yoho.anaithumfinal.Model.CartCountModel;
import com.yoho.anaithumfinal.Model.CartListModel;
import com.yoho.anaithumfinal.Model.CartResponse;
import com.yoho.anaithumfinal.Model.CategoryModel;
import com.yoho.anaithumfinal.Model.CheckResponse;
import com.yoho.anaithumfinal.Model.CouponModel;
import com.yoho.anaithumfinal.Model.FetchDeliveryModel;
import com.yoho.anaithumfinal.Model.GetOtpModel;
import com.yoho.anaithumfinal.Model.OrderDetailsModel;
import com.yoho.anaithumfinal.Model.OrderPlacedResponse;
import com.yoho.anaithumfinal.Model.OrderSpinnerModel;
import com.yoho.anaithumfinal.Model.OtpModel;
import com.yoho.anaithumfinal.Model.PastOrderModel;
import com.yoho.anaithumfinal.Model.ProductImagesModel;
import com.yoho.anaithumfinal.Model.ProductsModel;
import com.yoho.anaithumfinal.Model.ProductsOverviewModel;
import com.yoho.anaithumfinal.Model.ProfileModel;
import com.yoho.anaithumfinal.Model.SearchModel;
import com.yoho.anaithumfinal.Model.SupportModel;
import com.yoho.anaithumfinal.Model.SupportResponse;
import com.yoho.anaithumfinal.Model.TrackOrderModel;
import com.yoho.anaithumfinal.Model.ViewAddressModel;
import com.yoho.anaithumfinal.Model.ViewDetailsModel;
import com.yoho.anaithumfinal.Model.ViewProfileModel;
import com.yoho.anaithumfinal.Model.WishlistAddModel;
import com.yoho.anaithumfinal.Model.WishlistDeleteModel;
import com.yoho.anaithumfinal.Model.WishlistModel;

import okhttp3.ResponseBody;
import retrofit2.Call;


import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("category.php")
    Call<CategoryModel> getCategory();

    @GET("slider.php")
    Call<BannerModel>getBanner();

    @GET("products.php")
    Call<ProductsModel> getProducts(@Query("cat_id")String cat_id,@Query("id")String id);

    @GET("product_desc.php")
    Call<ProductsOverviewModel> getProductsOverview(@Query("product_id")String product_id,@Query("id")String id);

    @GET("product_images.php")
    Call<ProductImagesModel> getPimages(@Query("id")String id);

    @GET("get_mobile.php")
    Call<OtpModel> getOtp(@Query("mobile") String mobile);

    @GET("get_otp.php")
    Call<GetOtpModel> verifyOtp(@Query("mobile") String mobile, @Query("otp")String otp);

    @GET("search.php")
    Call<SearchModel> search(@Query("search")String search, @Query("id") String id);

    @GET("support_spinner.php")
    Call<SupportModel>getReason();

    @GET("order_spinner.php")
    Call<OrderSpinnerModel> getOrderId(@Query("id")String id);

    @GET("cart_count.php")
    Call<CartCountModel>getCartCount(@Query("id")String id);

    @GET("cart_list.php")
    Call<CartListModel>getCartList(@Query("id")String id);

    @GET("wishlist.php")
    Call<WishlistModel> getWishlist(@Query("id")String id);

    @GET("cart_delete.php")
    Call<ResponseBody> deleteCart(@Query("ca_id")String ca_id);

    @FormUrlEncoded
    @POST("wishlist_delete.php")
    Call<WishlistDeleteModel> deleteWishlist(@Field("w_id") String w_id);

    @FormUrlEncoded
    @POST("wishlist_add.php")
    Call<WishlistAddModel> addWishlist(@Field("id")String id,
                                       @Field("product_id")String product_id);
    @FormUrlEncoded
    @POST("add_profile.php")
    Call<ProfileModel> editProfile(@Field("id")String id,
                                   @Field("name")String name,
                                   @Field("email")String email);

    @GET("view_profile.php")
    Call<ViewProfileModel> getProfile(@Query("id")String id);

    @FormUrlEncoded
    @POST("cart.php")
    Call<CartResponse> Cart(@Field("status")String status,
                               @Field("id")String id,
                               @Field("product_id")String product_id);
    @GET("coupons.php")
    Call<CouponModel> getCoupon(@Query("id")String id);

    @GET("active_order.php")
    Call<ActiveOrderModel> getActiveOrder(@Query("id")String id);
    @GET("past_order.php")
    Call<PastOrderModel> getPastOrder(@Query("id")String id);

    @GET("view_details.php")
    Call<OrderDetailsModel> getOrderDetails(@Query("m_id")String m_id);

    @FormUrlEncoded
    @POST("support_desk.php")
    Call<SupportResponse> postSupport(@Field("order_id")String order_id,
                                      @Field("name")String name,
                                      @Field("id")String id,
                                      @Field("reason")String reason,
                                      @Field("description")String description);
    @GET("pincode.php")
    Call<AddPincodeModel> AddPincode();

    @GET("city.php")
    Call<AddCityModel> AddCity();

    @GET("state.php")
    Call<AddStateModel> AddState();

    @GET("area.php")
    Call<AddAreaModel> AddArea();

    @FormUrlEncoded
    @POST("add_address.php")
    Call<AddressResponse> addAddress(@Field("name")String name,
                                     @Field("address")String address,
                                     @Field("area")String area,
                                     @Field("city")String city,
                                     @Field("state")String state,
                                     @Field("pincode")String pincode,
                                     @Field("landmark")String landmark,
                                     @Field("alt_number")String alt_number,
                                     @Field("id")String id);

    @GET("view_details.php")
    Call<ViewDetailsModel> viewOrderDetails(@Query("m_id")String m_id);

    @GET("track_order.php")
    Call<TrackOrderModel> trackOrder(@Query("m_id")String m_id);
    @GET("fetch_delivery.php")
    Call<FetchDeliveryModel>fetchDeliveryMethod(@Query("id")String id);

    @GET("view_profile.php")
    Call<ViewAddressModel> getAddress(@Query("id") String id);

    
    @GET("coupon_apply.php")
    Call<ApplyCouponModel> applyCoupon(@Query("id")String id,
                                       @Query("amount")String amount,
                                       @Query("code")String code,
                                       @Query("delivery_mode")String delivery_mode);

    @FormUrlEncoded
    @POST("place_order.php")
    Call<OrderPlacedResponse>placeOrder(@Field("id")String id,
                                        @Field("shipping_method")String shipping_method,
                                        @Field("coupon_rupees")String coupon_rupees,
                                        @Field("total")String total,
                                        @Field("shipping_cost")String shipping_cost,
                                        @Field("tax_percent")String tax_percent,
                                        @Field("grand_total")String grand_total,
                                        @Field("payment_method")String payment_method);

    @GET("coupon_empty.php")
    Call<CheckResponse> getCheckDetails(@Query("id")String id, @Query("delivery_mode")String delivery_mode);

    @GET("notification.php")
    Call<NotificationModel>getNotifications();

}
