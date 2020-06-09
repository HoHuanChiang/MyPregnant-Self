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
        //database
        /*
        GetMainProducts getProducts=new GetMainProducts("all",
                "-1"
                ,String.valueOf(RepositoryFucntion.getSessionByWeek(pregnantWeek)), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {*/
                try {
                    String response="{\"week\":[{\"ProductID\":29,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=5385077&str_category_code=1204900017\",\"ProductName\":\"\\u3010BLACKMORES \\u6fb3\\u4f73\\u5bf6\\u3011\\u5b55\\u5bf6\\u591a\\u81a0\\u56ca\\u98df\\u54c1(180\\u9846)\",\"Category\":\"\\u7dad\\u4ed6\\u547d\",\"Content\":null},{\"ProductID\":48,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6561917&str_category_code=1204900028\",\"ProductName\":\"\\u3010\\u5b55\\u54fa\\u5152\\u3011\\u5abd\\u5abd\\u85fb\\u6cb9DHA\\u2605\\u8edf\\u81a0\\u56ca(\\u5bf6\\u5bf6\\u8070\\u660e\\u8d77\\u8dd1)\",\"Category\":\"DHA\",\"Content\":null},{\"ProductID\":63,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=1830500&str_category_code=1204900015\",\"ProductName\":\"\\u3010\\u4e09\\u591a\\u3011\\u5065\\u5eb7\\u7cfb\\u5217-T\\u5927\\u8c46\\u5375\\u78f7\\u8102\\u9846\\u7c92(300g\\/\\u7f50)\",\"Category\":\"\\u5375\\u78f7\\u8102\",\"Content\":null},{\"ProductID\":100,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=3985481&str_category_code=1204900017\",\"ProductName\":\"\\u3010\\u8d6b\\u800c\\u53f8\\u3011Ferti-500V\\u597d\\u97fb\\u65e5\\u672c\\u808c\\u9187+\\u8449\\u9178\\u690d\\u7269\\u81a0\\u56ca(90\\u9846\\/\\u7f50)\",\"Category\":\"\\u8449\\u9178\",\"Content\":\"\"}],\"others\":[[{\"ProductID\":46,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=5485260&str_category_code=1204900028&mdiv=1204900028-bt_9_002_01&ctype=B&Area=DgrpCategory\",\"ProductName\":\"\\u3010S-26\\u3011\\u5abd\\u54aaDHA\\u85fb\\u6cb9\\u81a0\\u56ca 60\\u7c92\\/\\u74f6\",\"Category\":\"DHA\",\"Content\":null},{\"ProductID\":47,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6243861&str_category_code=1204900028&mdiv=1204900028-bt_9_002_01&ctype=B&Area=DgrpCategory\",\"ProductName\":\"\\u3010GNC \\u5065\\u5b89\\u559c\\u3011\\u85fb\\u6cb9DHA\\u81a0\\u56ca 60\\u9846(\\u690d\\u7269\\u6027DHA)\",\"Category\":\"DHA\",\"Content\":null},{\"ProductID\":48,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6561917&str_category_code=1204900028\",\"ProductName\":\"\\u3010\\u5b55\\u54fa\\u5152\\u3011\\u5abd\\u5abd\\u85fb\\u6cb9DHA\\u2605\\u8edf\\u81a0\\u56ca(\\u5bf6\\u5bf6\\u8070\\u660e\\u8d77\\u8dd1)\",\"Category\":\"DHA\",\"Content\":null},{\"ProductID\":51,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6265523&str_category_code=1204900028\",\"ProductName\":\"\\u3010GNC \\u5065\\u5b89\\u559c\\u3011DHA\\u9b5a\\u6cb9600\\u81a0\\u56ca 60\\u9846(DHA\\/\\u03c9-3\\u8102\\u80aa\\u9178)\",\"Category\":\"DHA\",\"Content\":null},{\"ProductID\":52,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=5974457&str_category_code=1204900028\",\"ProductName\":\"\\u3010\\u8d6b\\u800c\\u53f8\\u3011\\u91d1\\u5de7Plus\\u690d\\u7269\\u8edf\\u81a0\\u56caLifesDHA\\u85fb\\u6cb9DHA200mg(60\\u9846\\/\\u7f50)\",\"Category\":\"DHA\",\"Content\":null}],[{\"ProductID\":60,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=5218740&str_category_code=1200600321&mdiv=1200600321-bt_9_002_01&ctype=B&Area=DgrpCategory\",\"ProductName\":\"\\u3010Sundown \\u65e5\\u843d\\u6069\\u8cdc\\u3011\\u9802\\u7d1a61%\\u5375\\u78f7\\u8102\\u81a0\\u56ca100\\u7c92(3\\u74f6\\u7d44)\",\"Category\":\"\\u5375\\u78f7\\u8102\",\"Content\":null},{\"ProductID\":61,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=5948087&str_category_code=1200600321&mdiv=1200600321-bt_9_002_01&ctype=B&Area=DgrpCategory\",\"ProductName\":\"\\u3010Vita Codes\\u3011\\u5927\\u8c46\\u80dc\\u592a\\u7fa4\\u7cbe\\u83ef\\u7f50\\u88dd450g\\u9644\\u6e6f\\u5319+\\u7dda\\u4e0a\\u98df\\u8b5c\",\"Category\":\"\\u5375\\u78f7\\u8102\",\"Content\":null},{\"ProductID\":62,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6561853&str_category_code=1204900015&mdiv=1204900015-bt_9_002_01&ctype=B&Area=DgrpCategory\",\"ProductName\":\"\\u3010\\u5b55\\u54fa\\u5152\\u3011\\u5375\\u78f7\\u8102\\u71d5\\u7aa9\\u2605\\u591a\\u6a5f\\u80fd\\u7d30\\u672b(\\u6dfb\\u52a0\\u91d1\\u7d72\\u71d5\\u7aa9)\",\"Category\":\"\\u5375\\u78f7\\u8102\",\"Content\":null},{\"ProductID\":63,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=1830500&str_category_code=1204900015\",\"ProductName\":\"\\u3010\\u4e09\\u591a\\u3011\\u5065\\u5eb7\\u7cfb\\u5217-T\\u5927\\u8c46\\u5375\\u78f7\\u8102\\u9846\\u7c92(300g\\/\\u7f50)\",\"Category\":\"\\u5375\\u78f7\\u8102\",\"Content\":null},{\"ProductID\":65,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6561856&str_category_code=1204900015\",\"ProductName\":\"\\u3010\\u5b55\\u54fa\\u5152\\u3011\\u5375\\u78f7\\u8102+\\u78f7\\u8102\\u8ceaPS\\u2605\\u81a0\\u56ca(\\u96d9\\u6548\\u5408\\u4e00)\",\"Category\":\"\\u5375\\u78f7\\u8102\",\"Content\":null}],[{\"ProductID\":75,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6947928&Area=search&mdiv=403&oid=1_1&cid=index&kw=%E5%A6%8A%E5%A8%A0%E6%B2%B9\",\"ProductName\":\"\\u3010\\u5abd\\u54aa\\u8389\\u5a1c\\u3011\\u7121\\u75d5\\u7f8e\\u9ad4\\u971c150ml+\\u5f48\\u529b\\u6f64\\u819a\\u6cb9100ml(\\u598a\\u5a20\\u971c\\/\\u598a\\u5a20\\u6cb9\\/\\u8eab\\u9ad4\\u6cb9)\",\"Category\":\"\\u598a\\u5a20\\u6cb9\\/\\u971c\",\"Content\":null},{\"ProductID\":76,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=3821592&Area=search&mdiv=403&oid=1_2&cid=index&kw=%E5%A6%8A%E5%A8%A0%E6%B2%B9\",\"ProductName\":\"\\u3010Mustela \\u6155\\u4e4b\\u606c\\u5eca\\u3011\\u6155\\u4e4b\\u5b55 \\u5b55\\u819a\\u6cb9 105ml(\\u598a\\u5a20\\u6cb9)\",\"Category\":\"\\u598a\\u5a20\\u6cb9\\/\\u971c\",\"Content\":null},{\"ProductID\":77,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=5594468&Area=search&mdiv=403&oid=1_13&cid=index&kw=%E5%A6%8A%E5%A8%A0%E6%B2%B9\",\"ProductName\":\"\\u3010CAREIN\\u9999\\u8349\\u7cbe\\u6cb9\\u5b78\\u82d1\\u3011\\u598a\\u5a20\\u6309\\u6469\\u6cb9 Pregnancy Massage Oil 100ml(\\u81c9\\u90e8\\u53ca\\u8eab\\u9ad4\\u6309\\u6469\\u6cb9\\u7cfb\\u5217\\u55ae\\u4ef6\\u7d44)\",\"Category\":\"\\u598a\\u5a20\\u6cb9\\/\\u971c\",\"Content\":null},{\"ProductID\":79,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6912528&Area=search&mdiv=403&oid=1_17&cid=index&kw=%E5%A6%8A%E5%A8%A0%E6%B2%B9\",\"ProductName\":\"\\u3010Fees \\u6cd5\\u7dfb\\u3011\\u7d93\\u5178\\u7f8e\\u819a\\u8b77\\u7406\\u6cb9-118ml(\\u5b55\\u671f \\u598a\\u5a20 \\u7686\\u9069\\u7528)\",\"Category\":\"\\u598a\\u5a20\\u6cb9\\/\\u971c\",\"Content\":null},{\"ProductID\":85,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=3821600&str_category_code=2705200386&mdiv=2705200386-bt_9_002_01&ctype=B&Area=DgrpCategory\",\"ProductName\":\"\\u3010Mustela \\u6155\\u4e4b\\u606c\\u5eca\\u3011\\u6155\\u4e4b\\u5b55 \\u64ab\\u7d0b\\u83c1\\u83ef 75ml\",\"Category\":\"\\u598a\\u5a20\\u6cb9\\/\\u971c\",\"Content\":null},{\"ProductID\":86,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6812044&str_category_code=2705200386\",\"ProductName\":\"\\u3010\\u65b0\\u808c\\u9713Ingeni\\u3011\\u7f8e\\u5b55\\u8a08\\u756b-\\u7121\\u7d0b\\u598a\\u5a20\\u6f64\\u819a\\u971c\",\"Category\":\"\\u598a\\u5a20\\u6cb9\\/\\u971c\",\"Content\":null},{\"ProductID\":87,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6361338&str_category_code=2705200386\",\"ProductName\":\"\\u3010CLARINS \\u514b\\u862d\\u8a69\\u3011\\u5b9b\\u82e5\\u65b0\\u751f\\u9664\\u7d0b\\u971c(200ml-\\u570b\\u969b\\u822a\\u7a7a\\u7248)\",\"Category\":\"\\u598a\\u5a20\\u6cb9\\/\\u971c\",\"Content\":null}],[{\"ProductID\":88,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=3581561&str_category_code=2705200385\",\"ProductName\":\"\\u3010\\u65bd\\u5df4\\u3011\\u885b\\u751f\\u8b77\\u6f54\\u9732200ml\\u9ec3\\u91d1\\u5973\\u90ce(\\u5feb\\u901f\\u5230\\u8ca8)\",\"Category\":\"\\u79c1\\u5bc6\\u4fdd\\u990a\",\"Content\":null},{\"ProductID\":89,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6859128&str_category_code=2705200385\",\"ProductName\":\"\\u3010Fees \\u6cd5\\u7dfb\\u3011\\u79c1\\u5bc6\\u5475\\u8b77\\u6d74\\u6f54\\u9732250ml(pH4.0 \\u4e7e\\u723d\\u8212\\u9069)\",\"Category\":\"\\u79c1\\u5bc6\\u4fdd\\u990a\",\"Content\":null},{\"ProductID\":90,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6864157&str_category_code=2705200385\",\"ProductName\":\"\\u3010\\u5abd\\u54aa\\u8389\\u5a1c\\u3011\\u8212\\u8b77\\u79c1\\u5bc6\\u6f54\\u9732200ml(\\u79c1\\u5bc6\\u6e05\\u6f54)\",\"Category\":\"\\u79c1\\u5bc6\\u4fdd\\u990a\",\"Content\":null},{\"ProductID\":91,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6947922&str_category_code=2705200385\",\"ProductName\":\"NeuDouche\\u95a8\\u871c\\u6db2-\\u9670\\u9053\\u6c96\\u6d17\\u6db2(\\u6b21\\u6c2f\\u9178 \\u79c1\\u5bc6\\u6de8\\u8b77 \\u5065\\u5eb7\\u9178\\u9e7c\\u503c \\u6297\\u83cc99.999% \\u9664\\u7570\\u5473)\",\"Category\":\"\\u79c1\\u5bc6\\u4fdd\\u990a\",\"Content\":null}],[{\"ProductID\":92,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=3821620&str_category_code=2705200387&mdiv=2705200387-bt_9_002_01&ctype=B&Area=DgrpCategory\",\"ProductName\":\"\\u3010Mustela \\u6155\\u4e4b\\u606c\\u5eca\\u3011\\u6155\\u4e4b\\u5b55 \\u7f8e\\u80f8\\u83c1\\u83ef 75ml\",\"Category\":\"\\u7f8e\\u80f8\\u4fdd\\u990a\",\"Content\":null},{\"ProductID\":93,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6009301&str_category_code=2705200387&mdiv=2705200387-bt_9_002_01&ctype=B&Area=DgrpCategory\",\"ProductName\":\"\\u3010PALMER\\u2019S \\u5e15\\u746a\\u6c0f\\u3011Q10\\u7f8e\\u80f8\\u7dca\\u7dfb\\u5f48\\u529b2\\u5165\\u7d44(\\u65b0\\u914d\\u65b9\\u6548\\u679c\\u5168\\u65b0\\u5347\\u7d1a)\",\"Category\":\"\\u7f8e\\u80f8\\u4fdd\\u990a\",\"Content\":null},{\"ProductID\":94,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6780231&str_category_code=2705200387\",\"ProductName\":\"\\u3010CLARINS \\u514b\\u862d\\u8a69\\u3011\\u8594\\u8587\\u679c\\u7f8e\\u80f8\\u971c-\\u8c50\\u6eff 50ML\\u3008\\u65b0\\u5305\\u88dd\\u3009\\u3008\\u767e\\u8ca8\\u516c\\u53f8\\u8ca8\\u3009\",\"Category\":\"\\u7f8e\\u80f8\\u4fdd\\u990a\",\"Content\":null},{\"ProductID\":95,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=3513858&str_category_code=2705200387\",\"ProductName\":\"\\u3010L\\u2019ERBOLARIO \\u857e\\u8389\\u6b50\\u3011\\u7f8e\\u80f8\\u7dca\\u7dfb\\u5f48\\u529b\\u971c 125ml(\\u7f8e\\u9ad4\\u7dca\\u7dfb\\u7cfb\\u5217)\",\"Category\":\"\\u7f8e\\u80f8\\u4fdd\\u990a\",\"Content\":null}],[{\"ProductID\":41,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6800791&str_category_code=1204900035\",\"ProductName\":\"\\u3010\\u60a0\\u6d3b\\u539f\\u529b\\u3011\\u9ad8\\u6fc3\\u5ea6\\u8513\\u8d8a\\u8393\\u79c1\\u5bc6\\u76ca\\u751f\\u83cc\\u690d\\u7269\\u81a0\\u56caX1\\u76d2(60\\u7c92\\/\\u76d2)\",\"Category\":\"\\u76ca\\u751f\\u83cc\",\"Content\":null},{\"ProductID\":42,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6004745&str_category_code=1204900035\",\"ProductName\":\"\\u3010\\u946b\\u8000\\u751f\\u6280\\u3011\\u5eb7\\u654f\\u4e00\\u751f\\u76ca\\u751f\\u83cc(30\\u5305\\u5165)\",\"Category\":\"\\u76ca\\u751f\\u83cc\",\"Content\":null},{\"ProductID\":43,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6390153&str_category_code=1204900035&mdiv=1204900035-bt_9_002_01&ctype=B&Area=DgrpCategory\",\"ProductName\":\"\\u3010Dr.Advice \\u5065\\u5eb7\\u529b\\u3011\\u76ca\\u66a2\\u654f\\u904e\\u654f\\u8178\\u9053\\u5065\\u5eb7\\u98df\\u54c1\\u96d9\\u8a8d\\u8b49\\u76ca\\u751f\\u83cc\\u25c6\\u51b7\\u85cf\\u914d\\u9001\\u25c630\\u5305\\/\\u76d2\",\"Category\":\"\\u76ca\\u751f\\u83cc\",\"Content\":null},{\"ProductID\":44,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6476170&str_category_code=1204900035\",\"ProductName\":\"\\u3010MIHONG\\u3011\\u9ad8\\u6548\\u76ca\\u751f\\u83ccx3\\u76d2\\u4efb\\u9078\\u7d44(\\u512a\\u683c\\/\\u9752\\u6885\\/\\u9cf3\\u68a8\\/\\u6a58\\u5b50\\/\\u8461\\u8404\\/\\u85cd\\u8393\\u4efb\\u9078)\",\"Category\":\"\\u76ca\\u751f\\u83cc\",\"Content\":null},{\"ProductID\":45,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=5708991&str_category_code=1204900035\",\"ProductName\":\"\\u3010\\u8461\\u8404\\u738b\\u3011\\u76ca\\u83cc\\u738b\\u7c89\\u672b\\u9846\\u7c9230\\u5165X2\\u76d2 \\u517160\\u5165(7\\u597d\\u83cc \\u7529\\u56e4\\u7a4d \\u597d\\u9806\\u66a2)\",\"Category\":\"\\u76ca\\u751f\\u83cc\",\"Content\":null}],[{\"ProductID\":66,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6531641&str_category_code=1204900032\",\"ProductName\":\"\\u3010\\u5b89\\u6eff\\u3011\\u5b55\\u5abd\\u5abd\\u5976\\u7c89900g*2\\u7f50\",\"Category\":\"\\u5abd\\u5abd\\u5976\\u7c89\",\"Content\":null},{\"ProductID\":67,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=5425878&str_category_code=1204900032&mdiv=1204900032-bt_9_002_01&ctype=B&Area=DgrpCategory\",\"ProductName\":\"\\u3010\\u7f8e\\u5f37\\u751f\\u3011\\u512a\\u751f\\u5abd\\u5abdA+\\u914d\\u65b9\\u5976\\u7c89 900g(\\u6e96\\u5099\\u61f7\\u5b55\\u53ca\\u54fa\\u4e73\\u5abd\\u5abd\\u9069\\u7528)\",\"Category\":\"\\u5abd\\u5abd\\u5976\\u7c89\",\"Content\":null},{\"ProductID\":68,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6617797&str_category_code=1204900032\",\"ProductName\":\"\\u3010QUAKER \\u6842\\u683c\\u3011\\u5abd\\u5abd\\u71df\\u990a\\u54c1(850gx2\\u7f50)\",\"Category\":\"\\u5abd\\u5abd\\u5976\\u7c89\",\"Content\":null},{\"ProductID\":69,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6093488&str_category_code=1204900032\",\"ProductName\":\"\\u3010\\u4e9e\\u57f9\\u3011\\u5fc3\\u7f8e\\u529b\\u5abd\\u5abd\\u71df\\u990a\\u54c1-\\u9999\\u8349\\u53e3\\u5473(36.5gx14\\u5305x2\\u76d2)\",\"Category\":\"\\u5abd\\u5abd\\u5976\\u7c89\",\"Content\":null}],[{\"ProductID\":35,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=3200898&str_category_code=1204900017\",\"ProductName\":\"\\u3010COSAM\\u3011\\u53ef\\u5584-\\u76c8\\u88dc\\u7d20(\\u91d1\\u512a\\u88dc\\u9435\\u80fd)-100\\u7c92\\u8edf\\u81a0\\u56ca\\/\\u74f6(3\\u74f6\\/\\u7d44)\",\"Category\":\"\\u7898\\u3001\\u9382\\u3001\\u9223\\u3001\\u9435\",\"Content\":null},{\"ProductID\":36,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6561919&str_category_code=1204900017\",\"ProductName\":\"\\u3010\\u5b55\\u54fa\\u5152\\u3011\\u9ad8\\u55ae\\u4f4d\\u51cd\\u6676\\u9435+\\u8449\\u9178\\u2605\\u81a0\\u56ca 60\\u7c92(\\u9435\\u5b9a\\u4e0d\\u80fd\\u5c11)\",\"Category\":\"\\u7898\\u3001\\u9382\\u3001\\u9223\\u3001\\u9435\",\"Content\":null},{\"ProductID\":37,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=4636876&str_category_code=1204900030\",\"ProductName\":\"\\u3010\\u9577\\u5e9a\\u751f\\u6280\\u3011\\u8907\\u5408\\u512a\\u9435(90\\u7c92\\/\\u74f6;\\u8edf\\u81a0\\u56ca)\",\"Category\":\"\\u7898\\u3001\\u9382\\u3001\\u9223\\u3001\\u9435\",\"Content\":null},{\"ProductID\":38,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6243858&str_category_code=1204900030\",\"ProductName\":\"\\u3010GNC \\u5065\\u5b89\\u559c\\u3011\\u8212\\u88dc\\u5e16\\u81a0\\u56ca 90\\u9846(\\u9435)\",\"Category\":\"\\u7898\\u3001\\u9382\\u3001\\u9223\\u3001\\u9435\",\"Content\":null},{\"ProductID\":40,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6643900&str_category_code=1204900030&mdiv=1204900030-bt_9_002_01&ctype=B&Area=DgrpCategory\",\"ProductName\":\"\\u3010\\u6b50\\u6d32\\u5c4b\\u3011\\u5fb7\\u570b\\u8349\\u672c\\u6db2-Floradix\\u9435\\u5143(5\\u74f6\\u8d85\\u503c\\u50f9)\",\"Category\":\"\\u7898\\u3001\\u9382\\u3001\\u9223\\u3001\\u9435\",\"Content\":null},{\"ProductID\":53,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6409495&str_category_code=1204900037&mdiv=1204900037-bt_9_002_01&ctype=B&Area=DgrpCategory\",\"ProductName\":\"\\u3010\\u633a\\u7acb\\u3011\\u9223\\u5f37\\u529b\\u9320\\u79ae\\u76d2176\\u9320(\\u5168\\u65b0\\u914d\\u65b9)\",\"Category\":\"\\u7898\\u3001\\u9382\\u3001\\u9223\\u3001\\u9435\",\"Content\":null},{\"ProductID\":54,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=2282140&str_category_code=1204900037\",\"ProductName\":\"\\u3010Sundown \\u65e5\\u843d\\u6069\\u8cdc\\u3011\\u7cbe\\u7d14\\u6ab8\\u6aac\\u9178\\u9223+\\u7dad\\u751f\\u7d20D3\\u5f37\\u5316\\u9320100\\u9320(3\\u74f6\\u7d44)\",\"Category\":\"\\u7898\\u3001\\u9382\\u3001\\u9223\\u3001\\u9435\",\"Content\":null},{\"ProductID\":55,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=4416634&str_category_code=1204900037\",\"ProductName\":\"\\u3010\\u6fb3\\u4f73\\u5bf6Blackmores\\u3011\\u6d3b\\u6027\\u9223+D3(120\\u9320)\",\"Category\":\"\\u7898\\u3001\\u9382\\u3001\\u9223\\u3001\\u9435\",\"Content\":null},{\"ProductID\":56,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6526328&str_category_code=1204900037\",\"ProductName\":\"\\u3010BHK's\\u3011\\u5b55\\u5abd\\u54aa\\u87af\\u5408\\u9223+D \\u7d20\\u98df\\u81a0\\u56ca(60\\u7c92\\/\\u76d2;2\\u76d2\\u7d44)\",\"Category\":\"\\u7898\\u3001\\u9382\\u3001\\u9223\\u3001\\u9435\",\"Content\":null},{\"ProductID\":57,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=5267375&str_category_code=1204900037\",\"ProductName\":\"\\u3010\\u591a\\u7acb\\u5eb7\\u3011\\u9223\\u9382\\u92c5+\\u7dad\\u751f\\u7d20D3(60\\u7c92\\/\\u74f6)\",\"Category\":\"\\u7898\\u3001\\u9382\\u3001\\u9223\\u3001\\u9435\",\"Content\":null},{\"ProductID\":58,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=1360404&str_category_code=1204900016&mdiv=1204900016-bt_9_002_01&ctype=B&Area=DgrpCategory\",\"ProductName\":\"\\u3010\\u6b50\\u6d32\\u5c4b\\u3011\\u5fb7\\u570b\\u8349\\u672c\\u6db2-\\u5927\\u88dc\\u5e16(\\u5929\\u7136\\u9223\\u9382\\u92c5+D)\",\"Category\":\"\\u7898\\u3001\\u9382\\u3001\\u9223\\u3001\\u9435\",\"Content\":null},{\"ProductID\":59,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=5661473&str_category_code=1204900016\",\"ProductName\":\"\\u3010\\u6c38\\u4fe1HAC\\u3011\\u6ab8\\u6aac\\u9178\\u9223\\u9320(120\\u9320\\/\\u74f6;2\\u74f6\\u7d44)\",\"Category\":\"\\u7898\\u3001\\u9382\\u3001\\u9223\\u3001\\u9435\",\"Content\":null}],[{\"ProductID\":8,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=4411193&str_category_code=1204900017\",\"ProductName\":\"\\u3010\\u6fb3\\u4f73\\u5bf6Blackmores\\u3011\\u5b55\\u8b77\\u8449\\u9178(90\\u9320)\",\"Category\":\"\\u8449\\u9178\",\"Content\":null},{\"ProductID\":13,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=4888298&str_category_code=1204900017\",\"ProductName\":\"\\u3010BHK's\\u3011\\u5b55\\u5abd\\u54aa\\u8449\\u9178\\u9320(90\\u7c92\\/\\u74f6)\",\"Category\":\"\\u8449\\u9178\",\"Content\":null},{\"ProductID\":15,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=4962353&str_category_code=1204900017\",\"ProductName\":\"\\u3010NOW\\u5065\\u800c\\u5a77\\u3011\\u5b55\\u5bf6\\u9320-B12+\\u8449\\u9178(250\\u9846\\/\\u74f6)\",\"Category\":\"\\u8449\\u9178\",\"Content\":null},{\"ProductID\":16,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=5809408&str_category_code=1204900017\",\"ProductName\":\"\\u3010Nutrimate \\u4f60\\u6ecb\\u7f8e\\u5f97\\u3011\\u8907\\u5408B12+\\u8449\\u917890\\u9846-1\\u5165\",\"Category\":\"\\u8449\\u9178\",\"Content\":null},{\"ProductID\":18,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6561919&str_category_code=1204900017\",\"ProductName\":\"\\u3010\\u5b55\\u54fa\\u5152\\u3011\\u9ad8\\u55ae\\u4f4d\\u51cd\\u6676\\u9435+\\u8449\\u9178\\u2605\\u81a0\\u56ca 60\\u7c92(\\u9435\\u5b9a\\u4e0d\\u80fd\\u5c11)\",\"Category\":\"\\u8449\\u9178\",\"Content\":null},{\"ProductID\":20,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=4825531&str_category_code=1204900017\",\"ProductName\":\"\\u3010AFC\\u3011\\u8449\\u9178\\u516d\\u74f6\\u7d44(\\u65e5\\u672c\\u539f\\u88dd)\",\"Category\":\"\\u8449\\u9178\",\"Content\":null},{\"ProductID\":21,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=5357004&str_category_code=1204900017\",\"ProductName\":\"\\u3010\\u591a\\u7acb\\u5eb7\\u3011\\u97fb\\u5b55\\u5abd\\u54aa\\u8449\\u9178+\\u808c\\u9187\\u81a0\\u56ca&\\u9223\\u9382\\u92c5+\\u7dad\\u751f\\u7d20D3(2\\u5165\\u7d44)\",\"Category\":\"\\u8449\\u9178\",\"Content\":null},{\"ProductID\":22,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=4411179&str_category_code=1204900017\",\"ProductName\":\"\\u3010\\u7f8e\\u570b\\u767e\\u4ed5\\u53ef\\u3011\\u5b55\\u54fa\\u8449\\u9178\\u81a0\\u56ca(3\\u74f6\\u7d44)\",\"Category\":\"\\u8449\\u9178\",\"Content\":null},{\"ProductID\":100,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=3985481&str_category_code=1204900017\",\"ProductName\":\"\\u3010\\u8d6b\\u800c\\u53f8\\u3011Ferti-500V\\u597d\\u97fb\\u65e5\\u672c\\u808c\\u9187+\\u8449\\u9178\\u690d\\u7269\\u81a0\\u56ca(90\\u9846\\/\\u7f50)\",\"Category\":\"\\u8449\\u9178\",\"Content\":\"\"}],[{\"ProductID\":80,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=5810532&str_category_code=1204900013&mdiv=1204900013-bt_9_002_01&ctype=B&Area=DgrpCategory\",\"ProductName\":\"\\u3010\\u7530\\u539f\\u9999-\\u6797\\u5fd7\\u73b2\\u63a8\\u85a6\\u3011\\u539f\\u5473\\/\\u539f\\u5473Plus\\/\\u7a7a\\u767d\\u6ef4\\u96de\\u7cbe 20\\u5165\\/60ml\",\"Category\":\"\\u6ef4\\u96de\\u7cbe\",\"Content\":null},{\"ProductID\":81,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=5804610&str_category_code=1204900033&mdiv=1204900033-bt_9_002_01&ctype=B&Area=DgrpCategory\",\"ProductName\":\"\\u3010\\u4eac\\u7d05\\u3011\\u6ef4\\u96de\\u7cbe20\\u5165*2\\u76d2(\\u52a0\\u8d082\\u5305\\u4eac\\u7d05\\u6ef4\\u96de\\u7cbe)\",\"Category\":\"\\u6ef4\\u96de\\u7cbe\",\"Content\":null},{\"ProductID\":82,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=5348265&str_category_code=1204900033&mdiv=1204900033-bt_9_002_01&ctype=B&Area=DgrpCategory\",\"ProductName\":\"\\u301080\\u5e74\\u8001\\u5b57\\u865f \\u8001\\u5354\\u73cd\\u3011\\u71ac\\u96de\\u7cbe\\u5e38\\u6eab\\u79ae\\u76d214\\u5165(42ml\\/\\u5165)x2\\u76d2\",\"Category\":\"\\u6ef4\\u96de\\u7cbe\",\"Content\":null},{\"ProductID\":83,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=4546115&str_category_code=1204900033\",\"ProductName\":\"\\u3010\\u91d1\\u724c\\u5927\\u5e2b \\u6ef4\\u96de\\u7cbe\\u30112 \\u76d2\\u5165(CP\\u503c\\u9ad8 \\u91d1\\u724c\\u5927\\u5e2b \\u6ef4\\u96de\\u7cbe)\",\"Category\":\"\\u6ef4\\u96de\\u7cbe\",\"Content\":null},{\"ProductID\":84,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=3923683&str_category_code=1204900033\",\"ProductName\":\"\\u3010\\u83ef\\u9640\\u6276\\u5143\\u5802\\u3011\\u9f9c\\u9e7f\\u71ac\\u96de\\u7cbe1\\u76d2(6\\u74f6\\/\\u76d2)\",\"Category\":\"\\u6ef4\\u96de\\u7cbe\",\"Content\":null}],[{\"ProductID\":24,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=5638725&str_category_code=1204900017\",\"ProductName\":\"\\u3010Sundown \\u65e5\\u843d\\u6069\\u8cdc\\u3011SUNVITE\\u5b55\\u5a66\\u5c08\\u7528\\u52a0\\u5f37\\u578b\\u7dad\\u751f\\u7d20+\\u85fb\\u6cb9DHA\\u8edf\\u81a0\\u56ca60\\u7c92(2\\u74f6\\u7d44)\",\"Category\":\"\\u7dad\\u4ed6\\u547d\",\"Content\":null},{\"ProductID\":26,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6561915&str_category_code=1204900017\",\"ProductName\":\"\\u3010\\u5b55\\u54fa\\u5152\\u3011\\u65b0\\u7dad\\u4ed6\\u547d\\u9320\\u2605120\\u7c92(\\u4e00\\u65e5\\u71df\\u990a\\u88dc\\u7d66)\",\"Category\":\"\\u7dad\\u4ed6\\u547d\",\"Content\":null},{\"ProductID\":27,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=4962353&str_category_code=1204900017\",\"ProductName\":\"\\u3010NOW\\u5065\\u800c\\u5a77\\u3011\\u5b55\\u5bf6\\u9320-B12+\\u8449\\u9178(250\\u9846\\/\\u74f6)\",\"Category\":\"\\u7dad\\u4ed6\\u547d\",\"Content\":null},{\"ProductID\":28,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=4796815&str_category_code=1204900017\",\"ProductName\":\"\\u3010NOW\\u5065\\u800c\\u5a77\\u3011\\u7d20\\u5bf6\\u9320-B12+\\u8449\\u9178(250\\u9846\\/\\u74f6)\",\"Category\":\"\\u7dad\\u4ed6\\u547d\",\"Content\":null},{\"ProductID\":29,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=5385077&str_category_code=1204900017\",\"ProductName\":\"\\u3010BLACKMORES \\u6fb3\\u4f73\\u5bf6\\u3011\\u5b55\\u5bf6\\u591a\\u81a0\\u56ca\\u98df\\u54c1(180\\u9846)\",\"Category\":\"\\u7dad\\u4ed6\\u547d\",\"Content\":null},{\"ProductID\":31,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=4452274&str_category_code=1204900026&mdiv=1204900026-bt_9_002_01&ctype=B&Area=DgrpCategory\",\"ProductName\":\"\\u3010\\u5584\\u5b58\\u3011\\u65b0\\u5bf6\\u7d0d\\u591a \\u5b55\\u5a66\\u7d9c\\u5408\\u7dad\\u4ed6\\u547d(200+30\\u9320)\",\"Category\":\"\\u7dad\\u4ed6\\u547d\",\"Content\":null},{\"ProductID\":32,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6264167&str_category_code=1204900026&mdiv=1204900026-bt_9_002_01&ctype=B&Area=DgrpCategory\",\"ProductName\":\"\\u3010GNC \\u5065\\u5b89\\u559c\\u3011\\u5a66\\u5bf6\\u6a02\\u98df\\u54c1\\u9320 120\\u9320(\\u8449\\u9178\\/\\u9435\\u8cea\\u9223\\u8cea\\/\\u7dad\\u4ed6\\u547dD\\/\\u5b55\\u5a66\\u7d9c\\u5408\\u7dad\\u4ed6\\u547d)\",\"Category\":\"\\u7dad\\u4ed6\\u547d\",\"Content\":null},{\"ProductID\":33,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=4970906&str_category_code=1204900026\",\"ProductName\":\"\\u3010\\u5927\\u6f22\\u9175\\u7d20\\u3011\\u5b55\\u990a\\u5b55\\u88dc\\u6db2(600mlx1\\u74f6)\",\"Category\":\"\\u7dad\\u4ed6\\u547d\",\"Content\":null},{\"ProductID\":34,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=5513917&str_category_code=1204900026\",\"ProductName\":\"\\u3010PrenaFemi \\u6c9b\\u7d0d\\u5983\\u3011\\u5973\\u6027\\u7d9c\\u5408\\u7dad\\u4ed6\\u547d\\u932060\\u9320\\/\\u74f6\",\"Category\":\"\\u7dad\\u4ed6\\u547d\",\"Content\":null}],[{\"ProductID\":70,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=5380776&str_category_code=1200600904&mdiv=1200600904-bt_9_002_01&ctype=B&Area=DgrpCategory\",\"ProductName\":\"\\u83ef\\u9f4a\\u5802\\u54c1\\u724c\\u71b1\\u92b7\\u7687\\u5e1d\\u91d1\\u7d72\\u71d5\\u7aa9\\u98f2\\u9650\\u5b9a\\u7d44\",\"Category\":\"\\u71d5\\u7aa9\",\"Content\":null},{\"ProductID\":71,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6618762&str_category_code=1200600904\",\"ProductName\":\"\\u990a\\u984f\\u56de\\u6625\\u65fa\\u904b\\u4f86\\u5fb7\\u5b89\\u5802\\u798f\\u9db4\\u71d5\\u7aa9\\u5927\\u7d44\",\"Category\":\"\\u71d5\\u7aa9\",\"Content\":null},{\"ProductID\":72,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=5179113&str_category_code=1200600904\",\"ProductName\":\"\\u3010\\u767d\\u862d\\u6c0f\\u3011\\u51b0\\u7cd6\\u71d5\\u7aa918\\u74f6(70g)\",\"Category\":\"\\u71d5\\u7aa9\",\"Content\":null},{\"ProductID\":73,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6377254&str_category_code=1200600904\",\"ProductName\":\"\\u3010UNISKIN\\u96f6\\u673a\\u9f61\\u3011\\u91d1\\u7d72\\u71d5\\u7aa9\\u81a0\\u539f\\u86cb\\u767d\\u98f2EX*4\\u76d2(\\u517148\\u74f6)\",\"Category\":\"\\u71d5\\u7aa9\",\"Content\":null},{\"ProductID\":74,\"Shop\":\"Momo\",\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6701273&str_category_code=1200600904\",\"ProductName\":\"\\u3010\\u8001\\u884c\\u5bb6\\u3011\\u51dd\\u7cb9\\u808c\\u598d\\u71d5\\u7aa9\\u98f2\\u79ae\\u76d2(14\\u74f6)\",\"Category\":\"\\u71d5\\u7aa9\",\"Content\":null}]],\"category\":[\"DHA\",\"\\u5375\\u78f7\\u8102\",\"\\u598a\\u5a20\\u6cb9\\/\\u971c\",\"\\u79c1\\u5bc6\\u4fdd\\u990a\",\"\\u7f8e\\u80f8\\u4fdd\\u990a\",\"\\u76ca\\u751f\\u83cc\",\"\\u5abd\\u5abd\\u5976\\u7c89\",\"\\u7898\\u3001\\u9382\\u3001\\u9223\\u3001\\u9435\",\"\\u8449\\u9178\",\"\\u6ef4\\u96de\\u7cbe\",\"\\u7dad\\u4ed6\\u547d\",\"\\u71d5\\u7aa9\"]}";
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
/*
            }
        });
        RequestQueue q= Volley.newRequestQueue(this);
        q.add(getProducts);*/
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
        //videoImage.setTag("http://163.25.101.128/pregnant/shop/"+sp.getProductID()+".jpg");
        videoImage.setImageDrawable(getDrawable(R.drawable.photo54));
        //new DownloadImageTask().execute(videoImage);

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
