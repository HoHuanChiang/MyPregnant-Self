package com.example.mypregnant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mypregnant.DatabaseClasses.GetMainProducts;
import com.example.mypregnant.Function.DownloadImageTask;
import com.example.mypregnant.Function.RepositoryFucntion;
import com.example.mypregnant.Function.ToolBarFunction;
import com.example.mypregnant.ObjectClasses.ShopProduct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShopTotalV2 extends AppCompatActivity {
    LinearLayout shopOthersLayout,shopCurrentWeekLayout,shopShowChooseLayout;
    int pregnantWeek;
    LayoutInflater inflater;
    View pareintView;
    DialogLoading loading;
    TextView shopSessionText,shopCategoryText,shopWeekTextView;
    boolean scrollDown;
    boolean optionOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_total_v2);
        ToolBarFunction.setToolBarInit(this,"營養品");
        shopCurrentWeekLayout=findViewById(R.id.shopCurrentWeekLayout);
        shopOthersLayout=findViewById(R.id.shopOthersLayout);
        shopSessionText=findViewById(R.id.shopSessionText);
        shopCategoryText=findViewById(R.id.shopCategoryText);
        shopShowChooseLayout=findViewById(R.id.shopShowChooseLayout);
        shopWeekTextView=findViewById(R.id.currentWeekText);
        loading=new DialogLoading(this);
        inflater = LayoutInflater.from(this);
        pareintView=inflater.inflate(R.layout.activity_shop_total_v2,null);
        pregnantWeek=getSharedPreferences("data",0).getInt("PregnantWeek",0);
        optionOpen=false;
        shopWeekTextView.setText(String.valueOf(pregnantWeek));
        loading.show();
        scrollDown=false;

        GetShopProduct();
    }
    public void GetShopProduct(){
        shopOthersLayout.removeAllViews();

        GetMainProducts getProducts=new GetMainProducts("all",
                "-1"
                ,String.valueOf(RepositoryFucntion.getSessionByWeek(pregnantWeek)), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject allProducts= new JSONObject(response);
                    JSONArray siftProducts=allProducts.getJSONArray("week");

                    for(int i=0;i<siftProducts.length();i++)
                    {
                        JSONObject jProduct=siftProducts.getJSONObject(i);
                        ShopProduct sp=new ShopProduct();
                        sp.setProductID(jProduct.getInt("ProductID"));
                        sp.setShop(jProduct.getString("Shop"));
                        sp.setLink(jProduct.getString("Link"));
                        sp.setProductName(jProduct.getString("ProductName"));
                        sp.setCategory(jProduct.getString("Category"));
                        sp.setContent(jProduct.getString("Content"));

                        View view=inflater.inflate(R.layout.layout_video_v2,(ViewGroup) pareintView,false);
                        setViewData(view,sp);
                        shopCurrentWeekLayout.addView(view);
                    }
                    siftProducts=allProducts.getJSONArray("others");
                    //全部都取道
                    if(siftProducts.length()!=1)
                    {
                        for(int i=0;i<siftProducts.length();i++)
                        {
                            JSONArray jEachCategory=siftProducts.getJSONArray(i);
                            View outterView=inflater.inflate(R.layout.layout_outter_horizontal,(ViewGroup) pareintView,false);
                            LinearLayout addLayout=outterView.findViewById(R.id.horizontalLayout);
                            TextView horizontalItemText=outterView.findViewById(R.id.horizontalItem);
                            for(int k=0;k<jEachCategory.length();k++)
                            {
                                JSONObject jEachProduct=jEachCategory.getJSONObject(k);
                                ShopProduct sp=new ShopProduct();
                                sp.setProductID(jEachProduct.getInt("ProductID"));
                                sp.setShop(jEachProduct.getString("Shop"));
                                sp.setLink(jEachProduct.getString("Link"));
                                sp.setProductName(jEachProduct.getString("ProductName"));
                                sp.setCategory(jEachProduct.getString("Category"));
                                sp.setContent(jEachProduct.getString("Content"));
                                View innerView=inflater.inflate(R.layout.layout_video_v2,(ViewGroup) pareintView,false);
                                setViewData(innerView,sp);
                                horizontalItemText.setText(sp.getCategory());
                                addLayout.addView(innerView);
                            }
                            shopOthersLayout.addView(outterView);

                        }
                    }//Category 單獨取 變成直列的linearlayout
                    else{
                        JSONArray jEachCategory=siftProducts.getJSONArray(0);
                        TextView textTile=new TextView(ShopTotalV2.this);
                        textTile.setTypeface(Typeface.DEFAULT_BOLD);
                        textTile.setTextSize(15);
                        shopOthersLayout.addView(textTile);
                        for(int k=0;k<jEachCategory.length();k+=2)
                        {
                            LinearLayout outterLayout=new LinearLayout(ShopTotalV2.this);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            outterLayout.setOrientation(LinearLayout.HORIZONTAL);
                            outterLayout.setLayoutParams(params);

                            /******把寬度設為等比*************/
                            View view=inflater.inflate(R.layout.layout_video_v2,(ViewGroup) pareintView,false);
                            LinearLayout innerLayout=view.findViewById(R.id.layoutVideoOutterLayout);
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
                            lp.weight=1;
                            innerLayout.setLayoutParams(lp);

                            textTile.setText(jEachCategory.getJSONObject(k).getString("Category"));
                            view=setViewData(view,setClasses(jEachCategory.getJSONObject(k)));
                            outterLayout.addView(view);

                            view=inflater.inflate(R.layout.layout_video_v2,(ViewGroup) pareintView,false);
                            /******把寬度設為等比*************/
                            innerLayout=view.findViewById(R.id.layoutVideoOutterLayout);
                            lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
                            lp.weight=1;
                            innerLayout.setLayoutParams(lp);
                            if(k+1<jEachCategory.length())
                            {

                                view=setViewData(view,setClasses(jEachCategory.getJSONObject(k+1)));
                            }
                            else
                            {
                                innerLayout.setVisibility(View.INVISIBLE);
                            }

                            outterLayout.addView(view);
                            shopOthersLayout.addView(outterLayout);
                        }
                    }

                    loading.dismiss();

                    //weekListView.setAdapter(new ShopAdapter(ShopWeek.this,products));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        RequestQueue q= Volley.newRequestQueue(this);
        q.add(getProducts);
    }
    public ShopProduct setClasses(JSONObject jeachData) throws JSONException {

        ShopProduct sp=new ShopProduct();
        sp.setProductID(jeachData.getInt("ProductID"));
        sp.setShop(jeachData.getString("Shop"));
        sp.setLink(jeachData.getString("Link"));
        sp.setProductName(jeachData.getString("ProductName"));
        sp.setCategory(jeachData.getString("Category"));
        sp.setContent(jeachData.getString("Content"));
        return sp;
    }
    public View setViewData(View view, final ShopProduct sp){
        TextView titleText=view.findViewById(R.id.layoutVideoTitleText);
        TextView weekText=view.findViewById(R.id.layoutVideoWeekText);
        titleText.setText(sp.getProductName());
        weekText.setText("第 "+sp.getPregnantWeek()+" 週");

        ImageView videoImage=view.findViewById(R.id.layoutVideoImage);
        videoImage.setTag("http://163.25.101.128/pregnant/shop/"+sp.getProductID()+".jpg");
        new DownloadImageTask().execute(videoImage);

        videoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sp.getLink()));
                startActivity(browserIntent);
            }
        });
        return view;
    }
}
