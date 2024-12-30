class ClientEntity {

    private static final ClientEntity CLIENT_ENTITY = new ClientEntity();
    public static ClientEntity getInstance() {return CLIENT_ENTITY;}
    private ClientEntity() {}

    private String username;

    private String content;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}