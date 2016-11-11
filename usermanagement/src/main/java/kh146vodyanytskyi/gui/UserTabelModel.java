package kh146vodyanytskyi.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import kh146vodyanytskyi.User;

public class UserTabelModel extends AbstractTableModel {

	private static final String[] COLUMN_NAMES = { "ID", "Имя", "Фамилия" };
	private static final Class[] COLUMN_CLASSES = {Long.class, String.class, String.class};
	private List users = null;

	public UserTabelModel(Collection users) {
		this.users = new ArrayList(users);
	}

	@Override
	public int getRowCount() {
		return users.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	public Class getColumnClass(int columnIndex) {
		return COLUMN_CLASSES[columnIndex];
	}

	public String getColumnName(int column) {
		return COLUMN_NAMES[column];
	}
	
	public User getUser(int index) {
        return (User) users.get(index);
    }
    
    public void addUsers(Collection users) {
        this.users.addAll(users);
    }
    
    public void clearUsers() {
        this.users = new ArrayList();
    }

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		User user = (User) users.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return user.getId();
		case 1:
			return user.getFirstName();
		case 2:
			return user.getLastName();
		}
		return null;
	}

}
