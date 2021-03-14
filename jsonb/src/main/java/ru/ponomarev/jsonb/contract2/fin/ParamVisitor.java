package ru.ponomarev.jsonb.contract2.fin;

import ru.ponomarev.jsonb.contract2.fin.entity.StringParam;

public interface ParamVisitor {

    void visitStringParam(StringParam param);
}
