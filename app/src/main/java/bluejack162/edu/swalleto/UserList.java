package bluejack162.edu.swalleto;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import bluejack162.edu.swalleto.models.User;

/**
 * Created by Thomas Asril on 6/20/2017.
 */

public class UserList extends ArrayAdapter<User> {

    private Activity context;
    private List<User> userList;

    public UserList(Activity context, List<User> userList) {
        super(context, R.layout.list_layout, userList);
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView tvName = (TextView) listViewItem.findViewById(R.id.tvName);
        //TextView tvEmail = (TextView) listViewItem.findViewById(R.id.tvEmail);
        //TextView tvPass = (TextView) listViewItem.findViewById(R.id.tvPassword);

        User user = userList.get(position);

        tvName.setText(user.getUserName());
        //tvEmail.setText(user.getUserEmail());
        //tvPass.setText(user.getUserPassword());

        return listViewItem;
    }
}
