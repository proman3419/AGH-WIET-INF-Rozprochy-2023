using System.Threading.Tasks;
using Grpc.Net.Client;
using DynamicCallsClient;
using Grpc.Net.Client.Configuration;

void execute(ExecutionService.ExecutionServiceClient client, String jarLocation, String className, String methodName, String data)
{
    try
    {
        var reply = client.execute(new ExecutionRequest
        {
            JarLocation = jarLocation,
            ClassName = className,
            MethodName = methodName,
            Data = data
        });

        if ("" != reply.Error)
        {
            Console.WriteLine($"Server returned an error: '{reply.Error}'");
        }
        else
        {
            Console.WriteLine($"Server returned a reply: '{reply.Data}'");
        }
    }
    catch (Grpc.Core.RpcException e)
    {
        Console.WriteLine($"RPC Error: {e.Message}");
    }
}

const string HOSTNAME = "localhost";
const int PORT = 13172;
using var channel = GrpcChannel.ForAddress($"http://{HOSTNAME}:{PORT}");
var client = new ExecutionService.ExecutionServiceClient(channel);
var quit = false;

while (!quit)
{
    Console.Write("> ");
    String executableName = Console.ReadLine();
    switch (executableName)
    {
        case "greet":
            execute(
                client: client,
                jarLocation: "DynamicCallsStatelessActions-1.0-SNAPSHOT.jar",
                className: "dynamiccalls.action.stateless.ImaginaryFriend",
                methodName: "greet",
                data: "Andrew"
                );
            break;
        case "add":
            execute(
                client: client,
                jarLocation: "DynamicCallsStatelessActions-1.0-SNAPSHOT.jar",
                className: "dynamiccalls.action.stateless.Calculator",
                methodName: "add",
                data: "[1, 2, -3, 123]"
                );
            break;
        case "multiply":
            execute(
                client: client,
                jarLocation: "DynamicCallsStatelessActions-1.0-SNAPSHOT.jar",
                className: "dynamiccalls.action.stateless.Calculator",
                methodName: "multiply",
                data: "[1, 2, 3, 4, 5]"
                );
            break;
        case "subtract":
            execute(
                client: client,
                jarLocation: "DynamicCallsStatelessActions-1.0-SNAPSHOT.jar",
                className: "dynamiccalls.action.stateless.Calculator",
                methodName: "subtract",
                data: "[1000, 1, 3]"
                );
            break;
        case "divide":
            execute(
                client: client,
                jarLocation: "DynamicCallsStatelessActions-1.0-SNAPSHOT.jar",
                className: "dynamiccalls.action.stateless.Calculator",
                methodName: "divide",
                data: "[120, 4, 2]"
                );
            break;
        case "divideBy0":
            execute(
                client: client,
                jarLocation: "DynamicCallsStatelessActions-1.0-SNAPSHOT.jar",
                className: "dynamiccalls.action.stateless.Calculator",
                methodName: "divide",
                data: "[120, 4, 0]"
                );
            break;
        case "addEntry1":
            execute(
                client: client,
                jarLocation: "",
                className: "dynamiccalls.action.stateful.blog.Blog",
                methodName: "addEntry",
                data: "{" +
                "\"title\": \"Summer in Italy\", " +
                "\"content\": \"Summer in Italy is a magical time of year, filled with endless sunshine, delectable cuisine, and stunning scenery. From the rolling hills of Tuscany to the pristine beaches of the Amalfi Coast, there\'s no shortage of picturesque destinations to explore. Whether you\'re sipping on a refreshing Aperol Spritz in a charming piazza or taking a leisurely boat ride along the coast, Italy\'s summer offerings are sure to leave you with unforgettable memories. So pack your bags and get ready to bask in the warm Italian sun – summer in Italy is an experience you won\'t want to miss!\", " +
                "\"tags\": [\"summer\", \"Italy\", \"adventure\"], " +
                "\"addedDate\": \"12-07-2022 16:34\"" +
                "}"
                );
            break;
        case "addEntry2":
            execute(
                client: client,
                jarLocation: "",
                className: "dynamiccalls.action.stateful.blog.Blog",
                methodName: "addEntry",
                data: "{" +
                "\"title\": \"Trains\", " +
                "\"content\": \"I like trains.\", " +
                "\"tags\": [\"trains\", \"I\", \"like\"], " +
                "\"addedDate\": \"03-01-2004 4:02\"" +
                "}"
                );
            break;
        case "getEntries":
            execute(
                client: client,
                jarLocation: "",
                className: "dynamiccalls.action.stateful.blog.Blog",
                methodName: "getEntries",
                data: ""
                );
            break;
        case "quit":
            Console.WriteLine("Quitting");
            quit = true;
            break;
        default:
            Console.WriteLine("Invalid command");
            break;
    }
}
