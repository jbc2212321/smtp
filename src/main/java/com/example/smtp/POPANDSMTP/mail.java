package POPANDSMTP;

import java.io.Serializable;

public class mail implements Serializable {
    String mail_cont,mail_subject,mail_source,mail_des;
    public mail createMail(String mail_cont,String mail_subject,String mail_source,String mail_des){
        this.mail_cont=mail_cont;
        this.mail_des=mail_des;
        this.mail_subject=mail_subject;
        this.mail_source=mail_source;
        return this;
    }

    public String toString(){
        return "mail{"+
                "mail_cont='"+mail_cont+'\''+
                "mail_des='"+mail_des+'\''+
                "mail_source='"+mail_source+'\''+
                "mail_subject='"+mail_subject+'\''+
                "}";
    }
}
