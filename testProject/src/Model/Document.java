package Model;
import java.sql.Date;

public class Document {
    
    private DocumentType type;
    private int documentNumber;
    private Date date;
    private float priceVATExcluded;
    private float priceVATIncluded;
    private Date paymentDeadline;
    private Workflow workflow;
    private DocumentStatus status;

    public Document(DocumentType type, int documentNumber, Date date, float priceVATExcluded, 
        float priceVATIncluded, Date paymentDeadline, Workflow workflow, DocumentStatus status){
            this.type = type;
            this.documentNumber = documentNumber;
            this.date = date;
            this.priceVATExcluded = priceVATExcluded;
            this.priceVATIncluded = priceVATIncluded;
            this.paymentDeadline = paymentDeadline;
            this.workflow = workflow;
            this.status = status;
    }

    public DocumentType getType() {
        return type;
    }

    public int getDocumentNumber() {
        return documentNumber;
    }

    public Date getDate() {
        return date;
    }

    public float getPriceVATExcluded() {
        return priceVATExcluded;
    }

    public float getPriceVATIncluded() {
        return priceVATIncluded;
    }

    public Date getPayementDeadline() {
        return paymentDeadline;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        StringBuilder docInformations = new StringBuilder(type + " n°" + documentNumber + ":\n");
        docInformations.append("    date: " + date.toString() + "\n");
        docInformations.append("    price:\n        without VAT: " + priceVATExcluded + "\n");
        docInformations.append("        with VAT: " + priceVATIncluded + "\n");
        docInformations.append("    Payement deadline: " + 
            (paymentDeadline != null? paymentDeadline.toString() : "none") + "\n");
        docInformations.append("    Workflow number: " + workflow.getId() + "\n");
        docInformations.append("    Status: " + status.getCode());
        return docInformations.toString();
    }
}
