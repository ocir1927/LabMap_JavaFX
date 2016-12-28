package lab.domain;

/**
 * Created with IntelliJ IDEA.
 * User: Costi
 * Date: 23.10.2016
 * Time: 12:19
 * To change this template use File | Settings | File Templates.
 */
public class ValidatorSarcina implements Validator<Sarcina> {
    @Override
    public void validate(Sarcina entity) throws ValidationException {
        String err="";
        if(entity.getDescriere().isEmpty()){
            err+="Descrierea nu poate fii vida\n";

        }
        if(entity.getId()<0){
            err+="Id-ul trebuie sa fie un intreg pozitiv\n";
        }

        if(err!=""){
            throw new ValidationException(err);
        }

    }
}
