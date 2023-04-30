using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using MySql.Data.MySqlClient;
namespace app
{
    public partial class Courses : UserControl
    {

        MySqlConnection conn = new MySqlConnection("server=localhost;database=oe_univ;UID=root;password=;SslMode=none");
        public Courses()
        {
            InitializeComponent();
        }

        private void Courses_Load(object sender, EventArgs e)
        {
            conn.Open();
            MySqlCommand cmd = new MySqlCommand("SELECT * FROM `courses` ORDER BY `course_name`" ,conn);
            MySqlDataReader reader = cmd.ExecuteReader();

            if (reader.HasRows)
            {
                while (reader.Read())
                {
                    dataGridView.Rows.Add(reader["course_id"], reader["course_name"], reader["department"], reader["staffID"]);
                }
                conn.Close();
            }
            else { MessageBox.Show("No data"); }
        }

        private void dataGridView_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
            tbid.Text = dataGridView.CurrentRow.Cells["id"].Value.ToString();
            tbCname.Text = dataGridView.CurrentRow.Cells["Cname"].Value.ToString();
            tbdepart.Text = dataGridView.CurrentRow.Cells["department"].Value.ToString();
            tbStaffID.Text = dataGridView.CurrentRow.Cells["staffid"].Value.ToString();

        }

        private void dataGridView_SelectionChanged(object sender, EventArgs e)
        {
            tbid.Text = dataGridView.CurrentRow.Cells["id"].Value.ToString();
            tbCname.Text = dataGridView.CurrentRow.Cells["Cname"].Value.ToString();
            tbdepart.Text = dataGridView.CurrentRow.Cells["department"].Value.ToString();
            tbStaffID.Text = dataGridView.CurrentRow.Cells["staffid"].Value.ToString();
        }

        private void add_Click(object sender, EventArgs e)
        {
            conn.Open();
            MySqlCommand cmd = new MySqlCommand("INSERT INTO `courses`(`course_id` , `course_name` , `department`  ,`staffID`) VALUES (@id, @cname , @depart  , @staffid) ", conn);
            cmd.Parameters.AddWithValue("@id", int.Parse(tbid.Text));
            cmd.Parameters.AddWithValue("@cname", tbCname.Text);
            cmd.Parameters.AddWithValue("@depart", tbdepart.Text);
            cmd.Parameters.AddWithValue("@staffid", int.Parse(tbStaffID.Text));
            cmd.ExecuteNonQuery();
            MessageBox.Show("ADD ");
            conn.Close();
          


        }

        private void button2_Click(object sender, EventArgs e)
        {
            tbid.Text = "";
            tbCname.Text = "";
            tbdepart.Text = "";
            tbStaffID.Text = "";
        }

        private void update_Click(object sender, EventArgs e)
        {
            conn.Open();
            MySqlCommand cmd = new MySqlCommand("UPDATE `courses` SET `course_id` = @id , `course_name` = @cname , `department`=@depart , `studentID` = @stdid ,`staffID` = @staffID WHERE `course_id`=@id OR course_name = @cname " ,conn);
            cmd.Parameters.AddWithValue("@id", int.Parse(tbid.Text));
            cmd.Parameters.AddWithValue("@cname", tbCname.Text);
            cmd.Parameters.AddWithValue("@depart", tbdepart.Text);
            cmd.Parameters.AddWithValue("@staffid", int.Parse(tbStaffID.Text));
            cmd.ExecuteNonQuery();
            MessageBox.Show("DATA saved sucsessfuly ");
            conn.Close();
        }
    }
}
