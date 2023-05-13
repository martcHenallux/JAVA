public class Contact {
    
    private BusinessEntity entity;
    private ContactType contactType;
    private String data;

    public Contact(BusinessEntity entity, ContactType contactType, String data){
        this.entity = entity;
        this.contactType = contactType;
        this.data = data;
    }

    public BusinessEntity getEntity() {
        return entity;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        StringBuilder information = new StringBuilder(entity.getLastName());
        if (!entity.getFirstName().isEmpty()){
            information.append(" " + entity.getFirstName());
        }
        information.append("\n" + contactType.getName() + ": " + data);
        return information.toString();
    }
}
