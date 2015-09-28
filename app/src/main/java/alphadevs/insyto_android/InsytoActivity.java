package alphadevs.insyto_android;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import alphadevs.insyto_android.adapter.MyRecyclerViewAdapter;
import alphadevs.insyto_android.data.InsyteItemData;

public class InsytoActivity extends AppCompatActivity
        implements InsyteFragmentList.OnInsyteListInteractionListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insyto);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_insyto_tabbed, menu);// TODO
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // TODO chnge test method
    @Override
    public void onSelectItem(String id)
    {
        /*InsyteFragment insyteFragment = InsyteFragment.newInstance(id, null);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.insyte_list, insyteFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();*/
    }

    private ArrayList<InsyteItemData> getDataSet() {
        ArrayList results = new ArrayList<InsyteItemData>();
        for (int index = 0; index < 20; index++) {
            InsyteItemData obj = new InsyteItemData();
            obj.setTitle("Some Primary Text " + index);
            obj.setDescription("Secondary " + index);
            obj.setThumbnail(1);// TODO what
            results.add(index, obj);
        }
        return results;
    }
}
