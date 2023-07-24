using System.Collections.Generic;
using System.Threading.Tasks;

using PageRu.Models;
using SQLite;

namespace PageRu.Data
{
    public class PageDB
    {
        readonly SQLiteAsyncConnection db;
        public PageDB(string connectionString)
        {
            db = new SQLiteAsyncConnection(connectionString);
            db.CreateTableAsync<Page>().Wait();
        }
        public Task<List<Page>> GetPagesAsync()
        {
            return db.Table<Page>().ToListAsync();
        }
        public Task<Page> GetPageAsync(int id)
        {
            return db.Table<Page>().Where(i => i.ID == id).FirstOrDefaultAsync();
        }
        public Task<int> SavePageAsync(Page page)
        {
            if (page.ID != 0)
            {
                return db.UpdateAsync(page);
            }
            else
            {
                return db.InsertAsync(page);
            }
        }
        public Task<int> DeletePageAsync(Page page)
        {
            return db.DeleteAsync(page);
        }
    }
}
