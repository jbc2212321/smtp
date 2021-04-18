package POPANDSMTP;

import java.io.Serializable;

public class mail implements Serializable {
    public String mail_cont,mail_subject,mail_source,mail_des,mail_from;
    public int mail_no;
    public mail createMail(String mail_cont,String mail_subject,String mail_source,String mail_des,String mail_from){
        this.mail_cont=mail_cont;
        this.mail_des=mail_des;
        this.mail_subject=mail_subject;
        this.mail_source=mail_source;
        this.mail_from=mail_from;
        return this;
    }

    public String toString(){
        return "mail{"+
                "mail_cont='"+mail_cont+'\''+
                "mail_des='"+mail_des+'\''+
                "mail_source='"+mail_source+'\''+
                "mail_subject='"+mail_subject+'\''+
                "mail_from='"+mail_from+'\''+
                "}";
    }
}
