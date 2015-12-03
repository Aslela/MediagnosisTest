package com.cyberits.mediagnosisv10;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cyberits.mediagnosisv10.custom.CustomActivity;
import com.cyberits.mediagnosisv10.model.CategoryUser;
import com.cyberits.mediagnosisv10.utils.Const;
import com.cyberits.mediagnosisv10.utils.Utils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by w7 on 30/11/2015.
 */
public class CategoryUserList extends CustomActivity {

    /** The Chat list. */
    private ArrayList<CategoryUser> cList;

    /** The user. */
    public static ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_user_list);

        getActionBar().setDisplayHomeAsUpEnabled(false);
        updateUserStatus(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        loadCategoryUserList();

    }

    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onDestroy()
     */
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        updateUserStatus(false);
    }

    /**
     * Update user status.
     *
     * @param online
     *            true if user is online
     */
    private void updateUserStatus(boolean online)
    {
        user.put("online", online);
        user.saveEventually();
    }

    /**
     * Load list of users.
     */
    private void loadCategoryUserList()
    {
        final ProgressDialog dia = ProgressDialog.show(this, null,
                getString(R.string.alert_loading));

        ParseQuery<ParseObject> query = ParseQuery.getQuery("CategoryUser");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> li, ParseException e) {
                dia.dismiss();
                if (li != null) {
                    //Log.d("score", "Retrieved " + li.size() + " scores");
                    cList = new ArrayList<CategoryUser>();
                    for (int i = 0; i < li.size(); i++)
                    {
                        ParseObject po = li.get(i);
                        CategoryUser category = new CategoryUser();
                        category.setObjectId(po.getObjectId());
                        category.setCategoryName(po.getString("categoryName"));
                        cList.add(category);
                    }
                    UserList.user = user;
                    ListView list = (ListView) findViewById(R.id.clist);
                    list.setAdapter(new CategoryUserAdapter());
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0,
                                                View arg1, int pos, long arg3)
                        {
                            String categoryId = cList.get(pos).getObjectId();
                            Intent intent = new Intent(CategoryUserList.this, UserList.class);
                            Bundle extras = new Bundle();
                            extras.putString("categoryID", categoryId);
                            intent.putExtras(extras);
                            startActivity(intent);
                        }
                    });


                } else {
                    Utils.showDialog(
                            CategoryUserList.this,
                            getString(R.string.err_users) + " "
                                    + e.getMessage());
                    e.printStackTrace();
                }
            }
        });

    }

    private class CategoryUserAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return cList.size();
        }

        @Override
        public CategoryUser getItem(int arg0) {
            return cList.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int pos, View v, ViewGroup arg2) {
            if (v == null)
                v = getLayoutInflater().inflate(R.layout.chat_item, null);

            CategoryUser c = getItem(pos);
            TextView lbl = (TextView) v;
            lbl.setText(c.getCategoryName());
            lbl.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_online,0, R.drawable.arrow, 0);

            return v;
        }
    }


}
