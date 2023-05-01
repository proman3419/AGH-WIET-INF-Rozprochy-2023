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

public interface FridgeWithShoppingList extends Fridge
{
    OrderedShoppingListRecord[] getShoppingList(com.zeroc.Ice.Current current)
        throws InStandbyModeError;

    ShoppingListRecord addShoppingListRecord(ShoppingListRecord record, com.zeroc.Ice.Current current)
        throws InStandbyModeError;

    ShoppingListRecord removeShoppingListRecord(int id, com.zeroc.Ice.Current current)
        throws InStandbyModeError,
               InvalidIndexError;

    /** @hidden */
    static final String[] _iceIds =
    {
        "::Ice::Object",
        "::SmartHome::Fridge",
        "::SmartHome::FridgeWithShoppingList",
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
        return "::SmartHome::FridgeWithShoppingList";
    }

    /**
     * @hidden
     * @param obj -
     * @param inS -
     * @param current -
     * @return -
     * @throws com.zeroc.Ice.UserException -
    **/
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_getShoppingList(FridgeWithShoppingList obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        com.zeroc.Ice.Object._iceCheckMode(com.zeroc.Ice.OperationMode.Idempotent, current.mode);
        inS.readEmptyParams();
        OrderedShoppingListRecord[] ret = obj.getShoppingList(current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        orderedShoppingListHelper.write(ostr, ret);
        ostr.writePendingValues();
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
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_addShoppingListRecord(FridgeWithShoppingList obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        final com.zeroc.IceInternal.Holder<ShoppingListRecord> icePP_record = new com.zeroc.IceInternal.Holder<>();
        istr.readValue(v -> icePP_record.value = v, ShoppingListRecord.class);
        istr.readPendingValues();
        inS.endReadParams();
        ShoppingListRecord iceP_record = icePP_record.value;
        ShoppingListRecord ret = obj.addShoppingListRecord(iceP_record, current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        ostr.writeValue(ret);
        ostr.writePendingValues();
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
    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_removeShoppingListRecord(FridgeWithShoppingList obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        int iceP_id;
        iceP_id = istr.readInt();
        inS.endReadParams();
        ShoppingListRecord ret = obj.removeShoppingListRecord(iceP_id, current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        ostr.writeValue(ret);
        ostr.writePendingValues();
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    /** @hidden */
    final static String[] _iceOps =
    {
        "addShoppingListRecord",
        "getCurrentTemperature",
        "getMode",
        "getShoppingList",
        "getTargetTemperature",
        "ice_id",
        "ice_ids",
        "ice_isA",
        "ice_ping",
        "notifyIfInStandbyMode",
        "removeShoppingListRecord",
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
                return _iceD_addShoppingListRecord(this, in, current);
            }
            case 1:
            {
                return Fridge._iceD_getCurrentTemperature(this, in, current);
            }
            case 2:
            {
                return SmartDevice._iceD_getMode(this, in, current);
            }
            case 3:
            {
                return _iceD_getShoppingList(this, in, current);
            }
            case 4:
            {
                return Fridge._iceD_getTargetTemperature(this, in, current);
            }
            case 5:
            {
                return com.zeroc.Ice.Object._iceD_ice_id(this, in, current);
            }
            case 6:
            {
                return com.zeroc.Ice.Object._iceD_ice_ids(this, in, current);
            }
            case 7:
            {
                return com.zeroc.Ice.Object._iceD_ice_isA(this, in, current);
            }
            case 8:
            {
                return com.zeroc.Ice.Object._iceD_ice_ping(this, in, current);
            }
            case 9:
            {
                return SmartDevice._iceD_notifyIfInStandbyMode(this, in, current);
            }
            case 10:
            {
                return _iceD_removeShoppingListRecord(this, in, current);
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
