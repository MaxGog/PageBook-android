using System;

using SQLite;


namespace PageRu.Models
{
    public class Page
    {
        [PrimaryKey, AutoIncrement]
        public int ID { get; set; }
        public string NamePage { get; set; }
        public string Content { get; set; }
        public DateTime Date { get; set; }
    }
}
