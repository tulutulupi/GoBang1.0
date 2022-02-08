public class Account {
    private String name;
    private String key;
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public Account(String name,String key,String phoneNumber){
        super();
        this.name = name;
        this.key = key;
        this.phoneNumber = phoneNumber;
    }
    public Account(){//json需要无参构造函数保证函数正常运行
        super();
    }

    @Override
    public String toString() {
        return "Account [name=" + name + ", key=" + key + ",phoneNum="+phoneNumber+"]";
    }
}
