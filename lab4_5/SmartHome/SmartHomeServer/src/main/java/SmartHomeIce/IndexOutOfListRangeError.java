//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.9
//
// <auto-generated>
//
// Generated from file `smarthome.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package SmartHomeIce;

public class IndexOutOfListRangeError extends com.zeroc.Ice.UserException
{
    public IndexOutOfListRangeError()
    {
    }

    public IndexOutOfListRangeError(Throwable cause)
    {
        super(cause);
    }

    public String ice_id()
    {
        return "::SmartHomeIce::IndexOutOfListRangeError";
    }

    /** @hidden */
    @Override
    protected void _writeImpl(com.zeroc.Ice.OutputStream ostr_)
    {
        ostr_.startSlice("::SmartHomeIce::IndexOutOfListRangeError", -1, true);
        ostr_.endSlice();
    }

    /** @hidden */
    @Override
    protected void _readImpl(com.zeroc.Ice.InputStream istr_)
    {
        istr_.startSlice();
        istr_.endSlice();
    }

    /** @hidden */
    public static final long serialVersionUID = 1863373410L;
}
