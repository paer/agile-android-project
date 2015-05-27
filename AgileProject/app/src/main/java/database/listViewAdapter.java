package database;

import java.sql.SQLException;
import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.paer.agileproject.R;

/**
 *
 * @author Mazen Mohamad
 */
public class listViewAdapter extends BaseAdapter
{
    public ArrayList<String> list;
    Activity activity;
    DialogInterface.OnClickListener dialogClickListener;

    public listViewAdapter(Activity activity, ArrayList<String> list) {
        super();
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    private class ViewHolder {
        TextView noteTExt;
        Button btn;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        // TODO Auto-generated method stub
        final ViewHolder holder;
        LayoutInflater inflater =  activity.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.note_list_item, null);
            holder = new ViewHolder();
            // holder.txtFirst = (TextView) convertView.findViewById(R.id.FirstText);
            holder.noteTExt = (TextView) convertView.findViewById(R.id.noteText);
            holder.btn = (Button) convertView.findViewById(R.id.delBut);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        //HashMap map = list.get(position);
        holder.noteTExt.setText(list.get(position));






        holder.btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        list.remove(position);
                                        DataBaseHandler dbh = new DataBaseHandler(activity);
                                        try {
                                            dbh.open(activity);
                                            dbh.deleteNote(holder.noteTExt.getText().toString());
                                            dbh.close();
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }

                                        notifyDataSetChanged();
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //No button clicked
                                        break;
                                }
                            }
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setMessage("Delete note \n Are you sure?").setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).show();

                    }
                }
        );
       /*holder.btn.setOnClickListener(
               new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       list.remove(position);
                       notifyDataSetChanged();
                   }
               }
       );*/
        return convertView;
    }

}

