package lab.domain;

/**
 * Created with IntelliJ IDEA.
 * User: Costi
 * Date: 23.10.2016
 * Time: 00:29
 * To change this template use File | Settings | File Templates.
 */
public class ValidatorPost implements Validator<Post> {
    @Override
    public void validate(Post entity) throws ValidationException {
        String err="";
        if(entity.getDenumire().isEmpty()){
            err+="Numele nu poate fii vid\n";

        }
        if(entity.getId()<0){
            err+="Id-ul trebuie sa fie un intreg pozitiv\n";
        }
        if (entity.getTip().isEmpty())
            err+="Tipul nu poate fii vid\n";

        if(err!=""){
            throw new ValidationException(err);
        }

    }
}
