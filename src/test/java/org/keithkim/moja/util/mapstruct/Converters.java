package org.keithkim.moja.util.mapstruct;

import org.keithkim.moja.util.NamedTuple;
import org.keithkim.moja.util.NamedTuple.MakeNamedPair;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Converters {
    @Mapper
    public interface MakeNamedTupleToMakeNamedPair {
        MakeNamedTupleToMakeNamedPair INSTANCE = Mappers.getMapper(MakeNamedTupleToMakeNamedPair.class);

        static MakeNamedPair<String, String> makeNamedPair(NamedTuple.MakeNamedTuple makeNamedTuple) {
            return (MakeNamedPair<String, String>) makeNamedTuple;
        }
    }

    @Mapper
    public interface ObjectToString {
        ObjectToString INSTANCE = Mappers.getMapper(ObjectToString.class);

        static String string(Object object) {
            return object == null ? null : object.toString();
        }
    }
}
