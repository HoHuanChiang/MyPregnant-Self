package com.example.mypregnant.AdapterClasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.mypregnant.EducationList2;
import com.example.mypregnant.EducationMain2;
import com.example.mypregnant.Function.RepositoryFucntion;
import com.example.mypregnant.HospitalEducationMain;

public class OnSwipeListener implements View.OnTouchListener {
    private final GestureDetector gestureDetector;
    public OnSwipeListener(Context context) {
        gestureDetector = new GestureDetector(context, new GestureListener(context));
    }
    public OnSwipeListener(Context context, Class nextActivity) {
        gestureDetector = new GestureDetector(context, new GestureListener(context,nextActivity));
    }
    public void onSwipeLeft() {

    }

    public void onSwipeRight() {
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }
    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_DISTANCE_THRESHOLD = 20;
        private static final int SWIPE_VELOCITY_THRESHOLD = 20;
        private Class nextActivity;
        private Context context;
        public GestureListener(Context context){
            this.nextActivity=null;
            this.context=context;
        }
        public GestureListener(Context context,Class nextActivity){
            this.nextActivity=nextActivity;


            this.context=context;
        }
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {

            int width= RepositoryFucntion.getScreenWidth((Activity) context);
            if(e.getX()<width*2/9)
            {
                onSwipeRight();
            }
            else if(e.getX()>width*7/9)
            {
                onSwipeLeft();
            }
            if(this.nextActivity!=null){

                Intent intent=new Intent();
                Class realActiviy=nextActivity;
                if(nextActivity.equals(EducationMain2.class)){
                    intent.putExtra("IsEducational","1");
                }
                else if(nextActivity.equals(HospitalEducationMain.class)){
                    realActiviy=EducationMain2.class;
                    intent.putExtra("IsEducational","0");
                }



                intent.setClass(context,realActiviy);
                context.startActivity(intent);
            }
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float distanceX = e2.getX() - e1.getX();
            float distanceY = e2.getY() - e1.getY();
            if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                if (distanceX > 0)
                    onSwipeRight();
                else
                    onSwipeLeft();
                return true;
            }

            return false;
        }
    }
}
