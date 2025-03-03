using SQLite;

namespace PageBook.Models;

public class ToDoModel
{
    [PrimaryKey]
    public string Id { get; set; }
    public string Title { get; set; }
    public string Description { get; set; }
    public DateTime CreatedAt { get; set; }
    public DateTime ModifiedAt { get; set; }
    public bool IsDo { get; set; }
    public string Tags { get; set; }
 }