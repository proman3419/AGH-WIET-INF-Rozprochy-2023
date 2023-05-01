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

package SmartHome;

public interface FridgeWithIceCubeMaker extends Fridge
{
    int getIceCubesMakerCapacity(com.zeroc.Ice.Current current);

    int getIceCubes(int count, com.zeroc.Ice.Current current)
        throws InStandbyModeError,
               NotEnoughIceCubesError;

    int getIceCubesCount(com.zeroc.Ice.Current current)
        throws InStandbyModeError;

    /** @hidden */
    static final String[] _iceIds =
    {
        "::Ice::Object",
        "::SmartHome::Fridge",
        "::SmartHome::FridgeWithIceCubeMaker",
        "::SmartHome::SmartDevice"
    };

    @Override
    default String[] ice_ids(com.zeroc.Ice.Current current)
    {
        return _iceIds;
    }

    @Override
    default String ice_id(com.zeroc.Ice.Current current)
    {
        return ice_staticId();
    }

    static String ice_staticId()
    {
        return "::SmartHome::FridgeWithIceCubeMaker";
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_getIceCubesMakerCapacity(FridgeWithIceCubeMaker obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(com.zeroc.Ice.OperationMode.Idempotent, current.mode);
        inS.readEmptyParams();
        int ret = obj.getIceCubesMakerCapacity(current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        ostr.writeInt(ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
     * @throws com.zeroc.Ice.UserException -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_getIceCubes(FridgeWithIceCubeMaker obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        int iceP_count;
        iceP_count = istr.readInt();
        inS.endReadParams();
        int ret = obj.getIceCubes(iceP_count, current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        ostr.writeInt(ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
     * @throws com.zeroc.Ice.UserException -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_getIceCubesCount(FridgeWithIceCubeMaker obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        com.zeroc.Ice.Object._iceCheckMode(com.zeroc.Ice.OperationMode.Idempotent, current.mode);
        inS.readEmptyParams();
        int ret = obj.getIceCubesCount(current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        ostr.writeInt(ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    /** @hidden */
    final static String[] _iceOps =
    {
        "getCurrentTemperature",
        "getIceCubes",
        "getIceCubesCount",
        "getIceCubesMakerCapacity",
        "getMode",
        "getTargetTemperature",
        "ice_id",
        "ice_ids",
        "ice_isA",
        "ice_ping",
        "notifyIfInStandbyMode",
        "setMode",
        "setTargetTemperature"
    };

    /** @hidden */
    @Override
    default java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceDispatch(com.zeroc.IceInternal.Incoming in, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        int pos = java.util.Arrays.binarySearch(_iceOps, current.operation);
        if(pos < 0)
        {
            throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
        }

        switch(pos)
        {
            case 0:
            {
                return Fridge._iceD_getCurrentTemperature(this, in, current);
            }
            case 1:
            {
                return _iceD_getIceCubes(this, in, current);
            }
            case 2:
            {
                return _iceD_getIceCubesCount(this, in, current);
            }
            case 3:
            {
                return _iceD_getIceCubesMakerCapacity(this, in, current);
            }
            case 4:
            {
                return SmartDevice._iceD_getMode(this, in, current);
            }
            case 5:
            {
                return Fridge._iceD_getTargetTemperature(this, in, current);
            }
            case 6:
            {
                return com.zeroc.Ice.Object._iceD_ice_id(this, in, current);
            }
            case 7:
            {
                return com.zeroc.Ice.Object._iceD_ice_ids(this, in, current);
            }
            case 8:
            {
                return com.zeroc.Ice.Object._iceD_ice_isA(this, in, current);
            }
            case 9:
            {
                return com.zeroc.Ice.Object._iceD_ice_ping(this, in, current);
            }
            case 10:
            {
                return SmartDevice._iceD_notifyIfInStandbyMode(this, in, current);
            }
            case 11:
            {
                return SmartDevice._iceD_setMode(this, in, current);
            }
            case 12:
            {
                return Fridge._iceD_setTargetTemperature(this, in, current);
            }
        }

        assert(false);
        throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
    }
}
