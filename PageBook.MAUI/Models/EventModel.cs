using SQLite;

namespace PageBook.Models;

public class Event
{
    [PrimaryKey]
    public string Id { get; set; }
    public string Title { get; set; }
    public string Description { get; set; }
    public DateTime CreatedAt { get; set; }
    public DateTime ModifiedAt { get; set; }
    public string Tags { get; set; }
 }