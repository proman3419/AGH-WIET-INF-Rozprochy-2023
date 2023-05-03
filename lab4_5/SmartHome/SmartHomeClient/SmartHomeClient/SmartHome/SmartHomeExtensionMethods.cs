using SmartHomeIce;

namespace SmartHomeClient.SmartHome
{
    public static class SmartHomeExtensionMethods
    {
        public static string ToPrettyString(this ShoppingListRecord record)
        {
            return $"Name: {record.name} | Quantity: {record.quantity} | Unit: {record.unit}";
        }
    }
}
