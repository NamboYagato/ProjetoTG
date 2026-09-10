package br.com.giovanni.projetotg.specification;

import br.com.giovanni.projetotg.enums.Cidades;
import br.com.giovanni.projetotg.enums.Estados;
import br.com.giovanni.projetotg.model.Mercado;
import org.springframework.data.jpa.domain.PredicateSpecification;

public class MercadoSpecification {
    public static PredicateSpecification<Mercado> onlyNome(String nome) {
        if (nome == null) {
            return PredicateSpecification.unrestricted();
        }
        return (from, builder) -> {
            return builder.like(builder.lower(from.get("nome")), "%" + nome.toLowerCase() + "%");
        };
    }

    public static PredicateSpecification<Mercado> onlyCidade(Cidades cidade) {
        if (cidade == null) {
            return PredicateSpecification.unrestricted();
        }
        return (from, builder) -> {
            return builder.equal(from.get("cidade"), cidade.name());
        };
    }

    public static PredicateSpecification<Mercado> onlyEstado(Estados estado) {
        if (estado == null) {
            return PredicateSpecification.unrestricted();
        }
        return (from, builder) -> {
            return builder.equal(from.get("estado"), estado.name());
        };
    }
}
