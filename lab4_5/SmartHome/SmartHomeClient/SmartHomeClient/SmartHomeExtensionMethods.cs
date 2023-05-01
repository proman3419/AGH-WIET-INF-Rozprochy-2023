using SmartHome;

namespace SmartHomeClient
{
    public static class SmartHomeExtensionMethods
    {
        public static string ToPrettyString(this ShoppingListRecord record)
        {
            return $"Name: {record.name} | Quantity: {record.quantity} | Unit: {record.unit}";
        }
    }
}
