#!/usr/bin/perl


use strict;
use warnings;
use Data::Dumper;


sub cleanupComicFileName {
    my $name = shift;
    if($name eq '(th)ink') {
        return 'Think';
    }
    $name =~ s/1/One/gs;
    $name =~ s/2/Two/gs;
    $name =~ s/3/Three/gs;
    $name =~ s/4/Four/gs;
    $name =~ s/5/Five/gs;
    $name =~ s/6/Six/gs;
    $name =~ s/7/Seven/gs;
    $name =~ s/8/Eight/gs;
    $name =~ s/9/Nine/gs;
    $name =~ s/0/Zero/gs;
    $name =~ s/\@/at/gs;
    $name =~ s/\&/n/gs;
    $name =~ s/[!'"\s.-]//gs;
    return $name;
}

sub cleanupPrefName {
    my $name = shift;
    $name =~ s/\@/At/gs;
    $name =~ s/\&/n/gs;
    $name =~ s/[!'"\s.-]//gs;
    return $name;
}

my $isEditorial = ((scalar(@ARGV) == 1) && ($ARGV[0] eq '-editorial'));
my $url = "http://www.gocomics.com";
my $mainurl = $isEditorial? "http://www.gocomics.com/explore/editorial_list" : "http://www.gocomics.com/features";
my $mainpage = "gocomics.mainpage";
my $allcomics = $isEditorial? "all_gocomics_editorial.txt" : "all_gocomics.txt";
my @comicslist;
my $cname;
my $fname;
my $actname;
my $yr;
my $mon;
my $dat;
my $JSON = $isEditorial? "classes_editorial.json" : "classes.json";
open JSON, ">$JSON";

print "Getting all comics page...\n";
system("rm -f $mainpage $allcomics");
system("wget '$mainurl' -O $mainpage --quiet");

print "Getting all comics list...\n";
open(ALL, "<$mainpage")  or  die("Failed to open '$mainpage' for reading!");
while(my $line = <ALL>) {
    chomp($line);
    $line =~ s/^\s+//;
    $line =~ s/\s+$//;
    if($line =~ /^<a href=\"(.*?)\" class=\"alpha_list.*\">(.*?)<\/a>/) {
	my $link = $url . $1;
	my $name = $2;
	push(@comicslist, [$link, $name, 0, 0]);
    }
}
close(ALL);

print "Getting all comics' feature-ID...\n";
my $temp_file_comic = "gocomic.temp.feature.id";
foreach my $comic (@comicslist) {
    print "  Working on '$comic->[1]'... ";
    system("rm -f $temp_file_comic");
    my $link = $comic->[0];
    system("wget '$link' -O $temp_file_comic --quiet");
    # feature-id
    $comic->[2] = 0;
    # first strip date
    my $first = `grep -i 'minDate' $temp_file_comic`;
    chomp($first);
    $first =~ s/.*minDate:\"//;
    $first =~ s/\",.*//;
    $yr=substr($first, 0, 4);
    $mon=substr($first, 5, 2);
    $dat=substr($first, 8, 2);
    $mon=$mon-1;
    $dat=$dat-1;
    $dat=$dat+1;
    $comic->[3] = "$yr/$mon/$dat";
    print " feature ID = $comic->[2]  first = $comic->[3]\n";
    $cname = $link;
    $cname =~ s/.*com\///;
    $fname = cleanupComicFileName($comic->[1]);
    $actname = $comic->[1];
    $fname =~ s/ //g;
    $comic->[3] =~ s/\//, /g;
    my $prefname = cleanupPrefName($cname);
    if($isEditorial) {
        print JSON "    {\"class\":\"GoComics.$fname\", \"name\":\"Editorial : $actname\", \"pref\":\"${prefname}Pref\"},\n";
    }
    else {
        print JSON "    {\"class\":\"GoComics.$fname\", \"name\":\"$actname\", \"pref\":\"${prefname}Pref\"},\n";
    }
    open CFILE, ">${fname}.java";
    print CFILE "package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class $fname extends DailyGoComicsCom {
	public $fname() {
		super();
		mComicName = \"$cname\";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set($comic->[3]);
	}
}
";
    close CFILE;
}

print "Storing all comics' info...\n";
open(ID, ">$allcomics")  or  die("Failed to open '$allcomics' for writing!");
print ID Dumper(\@comicslist);
close(ID);
close JSON;
print "Cleaning up...\n";
system("rm -f $mainpage $temp_file_comic");
