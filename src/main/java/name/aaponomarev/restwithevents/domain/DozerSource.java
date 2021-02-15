package name.aaponomarev.restwithevents.domain;

import lombok.Data;

import java.util.Collection;

@Data
public class DozerSource {

    private Collection<Foo> fooCollection;
}
