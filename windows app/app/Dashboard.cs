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
    public partial class Dashboard : UserControl
    {
        //VARIABLES
        MySqlConnection conn = new MySqlConnection("server=localhost;database=oe_univ;UID=root;password=;SslMode=none");
        int totalStd;
        int totalStdNotActive;
        int totalcourses;
        int totalStaff;

        public Dashboard()
        {
            InitializeComponent();
        }

        private void Dashboard_Load(object sender, EventArgs e)
        {
           
            conn.Open();
            MySqlCommand cmd = new MySqlCommand("SELECT COUNT(id) FROM `students` ", conn);
            MySqlDataReader countSTD = cmd.ExecuteReader();
            if(countSTD.HasRows)
            {
                while(countSTD.Read())
                {
                   totalStd = int.Parse(countSTD["COUNT(id)"].ToString());
                }
            }

            conn.Close();

            panalSTD.Height = totalStd *2;
            panalSTD.Location = new System.Drawing.Point(42,440-totalStd * 2);
            lblSTD.Location = new System.Drawing.Point(69, 319 - totalStd + 60);
            students.Text = totalStd.ToString();

            conn.Open();
            cmd = new MySqlCommand("SELECT COUNT(course_id) FROM `courses` ", conn);
            MySqlDataReader countCourses = cmd.ExecuteReader();

            if (countCourses.HasRows)
            {
                while (countCourses.Read())
                {
                    totalcourses = int.Parse(countCourses["COUNT(course_id)"].ToString());
                }
            }
            conn.Close();

            panalCoursess.Height = totalcourses * 2;
            panalCoursess.Location = new System.Drawing.Point(214,432-totalcourses*2);
            lblCourses.Location = new System.Drawing.Point(230, 319 - totalcourses + 60);
            courses.Text = totalcourses.ToString();

            conn.Open();
            cmd = new MySqlCommand("SELECT COUNT(id) FROM `students` WHERE active=@active", conn);
            cmd.Parameters.AddWithValue("@active", false);
            MySqlDataReader countStdNotActive = cmd.ExecuteReader();
            if(countStdNotActive.HasRows)
            {
                while(countStdNotActive.Read())
                {
                    totalStdNotActive = int.Parse(countStdNotActive["COUNT(id)"].ToString());
                }
            }
            else
            {
                MessageBox.Show("no std not active");
            }
            conn.Close();

            panalStdNotActive.Height = totalStdNotActive * 2;
            stdNotactive.Text = totalStdNotActive.ToString();
            panalStdNotActive.Location = new System.Drawing.Point(372, 432 - totalStdNotActive * 2);
            lblSTDNotActive.Location = new System.Drawing.Point(371, 319-totalStdNotActive+60);

            conn.Open();
            cmd = new MySqlCommand("SELECT COUNT(id) FROM `staff`", conn);
            cmd.Parameters.AddWithValue("@active", false);
            MySqlDataReader countStaff= cmd.ExecuteReader();
            if (countStaff.HasRows)
            {
                while (countStaff.Read())
                {
                    totalStaff = int.Parse(countStaff["COUNT(id)"].ToString());
                }
            }
            else
            {
                MessageBox.Show("no staff ");
            }
            conn.Close();

            panalStaff.Height = totalStaff * 2;
            panalStaff.Location = new System.Drawing.Point(541,432- totalStaff * 2);
            staff.Text = totalStdNotActive.ToString();
            lblstaff.Location = new System.Drawing.Point(522, 319 - totalStaff + 60);

            //Height > 435


        }
    }
}
