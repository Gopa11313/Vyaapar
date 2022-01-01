package net.com.gopal.vyapar.folder;

public class Folder {
    private String Id,name,description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    int icon;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public Folder() {
    }

    public Folder(String id, int icon, String name, String description) {
        Id = id;
        this.name = name;
        this.icon = icon;
        this.description=description;
    }
}
