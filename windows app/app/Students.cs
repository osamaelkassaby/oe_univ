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
    public partial class Students : UserControl
    {
        MySqlConnection conn2 = new MySqlConnection("server=localhost;database=oe_univ;UID=root;password=;SslMode=none;");
        string guideID;
        public Students()
        {
            InitializeComponent();
        }

        private void Students_Load(object sender, EventArgs e)
        {
            conn2.Open();
            MySqlCommand cmd = new MySqlCommand("SELECT * FROM `students` ORDER BY `firstname` ",conn2);
            
            MySqlDataReader reader = cmd.ExecuteReader();
            if(reader.HasRows)
            {
                while(reader.Read())
                {
                    dataGridView.Rows.Add(reader["id"].ToString() , reader["firstname"] , reader["lastname"] , reader["email"] , reader["phone"] , reader["birth"].ToString() , reader["gender"] , reader["address"] , reader["nationality"] , reader["national_identification"] , reader["active"], reader["GuideID"].ToString());
                }
            }
            conn2.Close();
        }

        private void dataGridView_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            tbID.Text = dataGridView.CurrentRow.Cells["id"].Value.ToString();
            tbFname.Text = dataGridView.CurrentRow.Cells["fname"].Value.ToString();
            tbLname.Text = dataGridView.CurrentRow.Cells["Lname"].Value.ToString();
            tbEmail.Text = dataGridView.CurrentRow.Cells["email"].Value.ToString();
            tbPhone.Text = dataGridView.CurrentRow.Cells["phone"].Value.ToString();
            tbBirht.Text = dataGridView.CurrentRow.Cells["birth"].Value.ToString();
            tbGender.Text = dataGridView.CurrentRow.Cells["gender"].Value.ToString();
            tbaddress.Text = dataGridView.CurrentRow.Cells["address"].Value.ToString();
            tbNationality.Text = dataGridView.CurrentRow.Cells["nationality"].Value.ToString();
            tbNID.Text = dataGridView.CurrentRow.Cells["national_id"].Value.ToString();
            tbActive.Text = dataGridView.CurrentRow.Cells["active"].Value.ToString();
            tbGuide.Text = dataGridView.CurrentRow.Cells["colGuideID"].Value.ToString();


        }

        private void button2_Click(object sender, EventArgs e)
        {
            tbFname.Text = "";
            tbLname.Text = "";
            tbEmail.Text = "";
            tbPhone.Text = "";
            tbBirht.Text = "";
            tbaddress.Text = "";
            tbNID.Text = "";
            tbActive.Text ="";

        }

        private void dataGridView_SelectionChanged(object sender, EventArgs e)
        {
            tbID.Text = dataGridView.CurrentRow.Cells["id"].Value.ToString();
            tbFname.Text = dataGridView.CurrentRow.Cells["fname"].Value.ToString();
            tbLname.Text = dataGridView.CurrentRow.Cells["Lname"].Value.ToString();
            tbEmail.Text = dataGridView.CurrentRow.Cells["email"].Value.ToString();
            tbPhone.Text = dataGridView.CurrentRow.Cells["phone"].Value.ToString();
            tbBirht.Text = dataGridView.CurrentRow.Cells["birth"].Value.ToString();
            tbGender.Text = dataGridView.CurrentRow.Cells["gender"].Value.ToString();
            tbaddress.Text = dataGridView.CurrentRow.Cells["address"].Value.ToString();
            tbNationality.Text = dataGridView.CurrentRow.Cells["nationality"].Value.ToString();
            tbNID.Text = dataGridView.CurrentRow.Cells["national_id"].Value.ToString();
            tbActive.Text = dataGridView.CurrentRow.Cells["active"].Value.ToString();
            tbGuide.Text = dataGridView.CurrentRow.Cells["colGuideID"].Value.ToString();

        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                conn2.Open();
                MySqlCommand cmd = new MySqlCommand("INSERT INTO `students`(`id`, `firstname`, `lastname`, `email`, `phone`, `birth`, `gender`, `address`, `nationality`, `national_identification`, `active` , `GuideID`) VALUES (@id,@fname,@lname,@email,@phone,@birth,@gender,@address,@nationality,@national_ID,@active , @guideID)", conn2);
                cmd.Parameters.AddWithValue("@id", int.Parse(tbID.Text));
                cmd.Parameters.AddWithValue("@fname", tbFname.Text);
                cmd.Parameters.AddWithValue("@lname", tbLname.Text);
                cmd.Parameters.AddWithValue("@email", tbEmail.Text);
                cmd.Parameters.AddWithValue("@phone", tbPhone.Text);
                cmd.Parameters.AddWithValue("@birth", tbBirht.Text);
                cmd.Parameters.AddWithValue("@gender", tbGender.Text);
                cmd.Parameters.AddWithValue("@address", tbaddress.Text);
                cmd.Parameters.AddWithValue("@nationality", tbNationality.Text);
                cmd.Parameters.AddWithValue("@national_ID", int.Parse(tbNID.Text));
                cmd.Parameters.AddWithValue("@active", Boolean.Parse(tbActive.Text));
                cmd.Parameters.AddWithValue("@guideID", int.Parse(tbGuide.Text));

                cmd.ExecuteNonQuery();
                MessageBox.Show("data saved done");
                conn2.Close();

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.ToString());
            }
        }

        private void update_Click(object sender, EventArgs e)
        {
            conn2.Open();
            MySqlCommand cmd = new MySqlCommand("UPDATE `students` SET `firstname` = @fname , `lastname` = @lname , `email`=@email , `phone` = @phone , `address` = @address , `active` = @active WHERE `id` = @id AND `national_identification`=@national_ID", conn2);
            cmd.Parameters.AddWithValue("@id", int.Parse(tbID.Text));
            cmd.Parameters.AddWithValue("@fname", tbFname.Text);
            cmd.Parameters.AddWithValue("@lname", tbLname.Text);
            cmd.Parameters.AddWithValue("@email", tbEmail.Text);
            cmd.Parameters.AddWithValue("@phone", tbPhone.Text);
            cmd.Parameters.AddWithValue("@address", tbaddress.Text);
            cmd.Parameters.AddWithValue("@nationality", tbNationality.Text);
            cmd.Parameters.AddWithValue("@national_ID", int.Parse(tbNID.Text));
            cmd.Parameters.AddWithValue("@active", Boolean.Parse(tbActive.Text));
            try {
                cmd.ExecuteNonQuery();
                MessageBox.Show("data saved done");
                } 
            catch(Exception ex)
            {
                MessageBox.Show("error"+ex);
            }
            conn2.Close();
        }
    }
}
