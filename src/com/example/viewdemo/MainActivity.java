package com.example.viewdemo;




import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class MainActivity extends Activity implements OnClickListener{
	private static final String TAG="MainActivity";

	private LinearLayout leftMenu;
    private LinearLayout content;
    private LinearLayout rightMenu;
    
    private LayoutParams leftMenuParams;
    private LayoutParams contentParams;
    private LayoutParams rightMenuParams;

    // menu完全显示时，留给content的宽度值。
    private static final int menuPadding = 120;
    private ImageView leftBtn,rightBtn;

    // 分辨率
    private int disPlayWidth;
   
    private boolean mIsShow = false;
    private boolean mIsRightShow = false;
    
    private static final int speed = 50;
    public boolean isMenu=false;
    public boolean isRightMenu=false;
    
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
	}
	
	
	

	/**初始化视图
	 * TODO
	 */
	private void initView() {
		// TODO Auto-generated method stub
		disPlayWidth = getWindowManager().getDefaultDisplay().getWidth();
		leftMenu = (LinearLayout) findViewById(R.id.leftMenu);
		leftMenu.setOnClickListener(this);
        content = (LinearLayout) findViewById(R.id.content);
        content.setOnClickListener(this);
        rightMenu = (LinearLayout) findViewById(R.id.rightMenu);
        rightMenu.setOnClickListener(this);
        
        
        leftMenuParams = (LayoutParams) leftMenu.getLayoutParams();
        contentParams = (LayoutParams) content.getLayoutParams();
        rightMenuParams= (LayoutParams) rightMenu.getLayoutParams();

        leftMenuParams.width = disPlayWidth - menuPadding;
        contentParams.width = disPlayWidth;
        rightMenuParams.width = disPlayWidth - menuPadding;
        
        leftBtn=(ImageView)findViewById(R.id.leftBtn);
        rightBtn=(ImageView)findViewById(R.id.rightBtn);
        
        leftBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
        
        showMenu(mIsShow);
        showRightMenu(mIsRightShow);
	}



	/**
	 * 显示界面
	 * TODO
	 * @param isShow
	 */
	private void showMenu(boolean isShow)
    {
        if (isShow)
        {
            mIsShow = true;
            leftMenuParams.leftMargin = 0;
        } else
        {
            mIsShow = false;
            leftMenuParams.leftMargin = 0 - leftMenuParams.width;
        }
        leftMenu.setLayoutParams(leftMenuParams);
    }
	
	private void showRightMenu(boolean isShow)
    {
        if (isShow)
        {
        	mIsRightShow = true;
        	contentParams.leftMargin = 0 ;
        } else
        {
        	mIsRightShow = false;
        	contentParams.leftMargin  =  0;
        }
        content.setLayoutParams(contentParams);
    }

	  /**
	   * 
	   * TODO 控制左侧View
	   * @author cuiran
	   * @version 1.0.0
	   */
    class showMenuAsyncTask extends AsyncTask<Integer, Integer, Integer>
    {

        @Override
        protected Integer doInBackground(Integer... params)
        {
            int leftMargin =leftMenuParams.leftMargin;
            Log.i(TAG, "leftMargin="+leftMargin);
            while (true)
            {
                leftMargin += params[0];
                if (params[0] > 0 && leftMargin >= 0)
                {
                    break;
                } else if (params[0] < 0 && leftMargin <= -leftMenuParams.width)
                {
                    break;
                }
                
                Log.i(TAG, "doInBackground:leftMargin"+leftMargin);
                publishProgress(leftMargin);
                try
                {
                    Thread.sleep(30);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            return leftMargin;
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
            leftMenuParams.leftMargin = values[0];
            leftMenu.setLayoutParams(leftMenuParams);
        }

        @Override
        protected void onPostExecute(Integer result)
        {
            super.onPostExecute(result);
            leftMenuParams.leftMargin = result;
            leftMenu.setLayoutParams(leftMenuParams);
        }

    }
    
    /**
	   * 
	   * TODO 控制右侧View
	   * @author cuiran
	   * @version 1.0.0
	   */
  class showRightMenuAsyncTask extends AsyncTask<Integer, Integer, Integer>
  {

      @Override
      protected Integer doInBackground(Integer... params)
      {
          int leftMargin =contentParams.leftMargin;
          while (true)
          {
        	  leftMargin += params[0];
        	  if (params[0] > 0 && leftMargin >= 0)
              {
                  break;
              } else if (params[0] < 0 && leftMargin <= -contentParams.width+menuPadding)
              {
                  break;
              }
              publishProgress(leftMargin);
              try
              {
                  Thread.sleep(30);
              } catch (InterruptedException e)
              {
                  e.printStackTrace();
              }
          }
          return leftMargin;
      }

      @Override
      protected void onProgressUpdate(Integer... values)
      {
    	  
          super.onProgressUpdate(values);
          contentParams.leftMargin = values[0];
          content.setLayoutParams(contentParams);
      }

      @Override
      protected void onPostExecute(Integer result)
      {
    	  Log.i(TAG, "onPostExecute");
          super.onPostExecute(result);
          contentParams.leftMargin = result;
          content.setLayoutParams(contentParams);
      }

  }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		  switch (v.getId())
	        {
	        case R.id.leftMenu:
	        	isMenu=false;
	        	 new showMenuAsyncTask().execute(-speed);
	            break;
	        case R.id.leftBtn:
	        	
	        	if(isMenu){
	        		isMenu=false;
	        		 new showMenuAsyncTask().execute(-speed);
	        	}else{
	        		isMenu=true;
	        		new showMenuAsyncTask().execute(speed);
	        	}
	        	
		         break;
	        case R.id.rightBtn:
	        	Log.i(TAG, "点击rightBtn");
	        	if(isRightMenu){
	        		isRightMenu=false;
	        		new showRightMenuAsyncTask().execute(speed);
	        	}else{
	        		isRightMenu=true;
	        		 new showRightMenuAsyncTask().execute(-speed);
	        	}
	        	break;
	        case R.id.content:
	           
	            break;
	        }
	}

}
